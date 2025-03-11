package br.edu.ifma.microsservices.mscartoes.service;

import br.edu.ifma.microsservices.mscartoes.domain.ClienteCartao;
import br.edu.ifma.microsservices.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> getCartoesByCpf(String cpf){
        return repository.findByCpf(cpf);
    }

}
