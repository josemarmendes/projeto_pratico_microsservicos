package br.edu.ifma.microsservices.mscartoes.controller;

import br.edu.ifma.microsservices.mscartoes.domain.Cartao;
import br.edu.ifma.microsservices.mscartoes.domain.ClienteCartao;
import br.edu.ifma.microsservices.mscartoes.domain.representation.CartaoSaveRequest;
import br.edu.ifma.microsservices.mscartoes.domain.representation.CartoesPorClienteResponse;
import br.edu.ifma.microsservices.mscartoes.service.CartaoService;
import br.edu.ifma.microsservices.mscartoes.service.ClienteCartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final CartaoService service;

    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){
        Cartao cartao = request.toModel();
        service.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = service.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam String cpf){

        List<ClienteCartao> lista = clienteCartaoService.getCartoesByCpf(cpf);

        List<CartoesPorClienteResponse> resultList = lista.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }
}
