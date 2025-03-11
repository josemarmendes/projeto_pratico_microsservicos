package br.edu.ifma.microsservices.msclientes.service;

import br.edu.ifma.microsservices.msclientes.domain.Cliente;
import br.edu.ifma.microsservices.msclientes.domain.exception.DomainException;
import br.edu.ifma.microsservices.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente save(Cliente cliente) {
        boolean clienteEmUso = clienteRepository.findByCpf(cliente.getCpf())
                .stream()
                .anyMatch(clienteCadastrado -> !clienteCadastrado.equals(cliente));

        if(clienteEmUso){
            throw new DomainException("Cliente já cadastrado");
        }

        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new DomainException("Erro ao cadastrar cliente: CPF já cadastrado.");
        }

    }

    @Transactional
    public Optional<Cliente> getByCpf(String cpf){
        return clienteRepository.findByCpf(cpf);
    }

}
