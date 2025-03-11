package br.edu.ifma.microservices.msavaliadorcredito.service;

import br.edu.ifma.microservices.msavaliadorcredito.domain.model.*;
import br.edu.ifma.microservices.msavaliadorcredito.exception.DadosClienteNotFoundException;
import br.edu.ifma.microservices.msavaliadorcredito.exception.ErroComunicacaoMicroservicesException;
import br.edu.ifma.microservices.msavaliadorcredito.exception.ErroSolicitacaoCartaoException;
import br.edu.ifma.microservices.msavaliadorcredito.infra.clients.CartoesResourceClient;
import br.edu.ifma.microservices.msavaliadorcredito.infra.clients.ClienteResourceClient;
import br.edu.ifma.microservices.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;

    private final CartoesResourceClient cartoesResourceClient;

    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();

            List<CartaoAprovado> listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var multiplicadorDeLimite = idadeBD.divide(BigDecimal.valueOf(10));

                BigDecimal limiteAprovado = multiplicadorDeLimite.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(dadosSolicitacaoEmissaoCartao.getCpf());

            DadosCliente dadosCliente = dadosClienteResponse.getBody();
            String nomeDoCliente = dadosCliente.getNome();


            if(dadosCliente != null){
                dadosSolicitacaoEmissaoCartao.setNomeCliente(dadosCliente.getNome());
                log.info("Log dos dados do cliente: " + dadosCliente + " nome do cliente: " + nomeDoCliente);
            }

            log.info("Log dos dados da solicitação do cartão : " + dadosSolicitacaoEmissaoCartao);
            emissaoCartaoPublisher.solicitarCartao(dadosSolicitacaoEmissaoCartao);

            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo, nomeDoCliente, dadosSolicitacaoEmissaoCartao.getCpf(), dadosSolicitacaoEmissaoCartao.getEndereco());

        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
