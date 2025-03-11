package br.edu.ifma.microservices.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProtocoloSolicitacaoCartao {

    private String protocolo;
    private String nomeCliente;
    private String cpf;
    private String endereco;
}
