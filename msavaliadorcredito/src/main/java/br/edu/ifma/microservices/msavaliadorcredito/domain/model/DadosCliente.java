package br.edu.ifma.microservices.msavaliadorcredito.domain.model;

import lombok.Data;

@Data
public class DadosCliente {

    private String cpf;
    private String nome;
    private Integer idade;
}
