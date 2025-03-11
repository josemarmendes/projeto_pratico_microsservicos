package br.edu.ifma.microsservices.mscartoes.infra.mqueue;

import br.edu.ifma.microsservices.mscartoes.domain.Cartao;
import br.edu.ifma.microsservices.mscartoes.domain.ClienteCartao;
import br.edu.ifma.microsservices.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import br.edu.ifma.microsservices.mscartoes.infra.repository.CartaoRepository;
import br.edu.ifma.microsservices.mscartoes.infra.repository.ClienteCartaoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        log.info("payload da solicitação do cartão: " + payload);
        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
            salvarClienteCartao(dados, cartao);

        } catch (Exception e) {
           log.error("Erro ao processar solicitação de emissao de cartão: {}", e.getMessage());
        }
    }

    private void salvarClienteCartao(DadosSolicitacaoEmissaoCartao dados, Cartao cartao) {
        ClienteCartao clienteCartao = new ClienteCartao();
        clienteCartao.setCartao(cartao);
        clienteCartao.setNomeCliente(dados.getNomeCliente());
        clienteCartao.setCpf(dados.getCpf());
        clienteCartao.setLimite(dados.getLimiteLiberado());

        log.info("Nome do cliente ao salvar o ClienteCartao: " + clienteCartao.getNomeCliente());

        clienteCartaoRepository.save(clienteCartao);
    }


}
