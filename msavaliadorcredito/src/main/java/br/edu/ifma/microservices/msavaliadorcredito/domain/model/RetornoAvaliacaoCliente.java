package br.edu.ifma.microservices.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RetornoAvaliacaoCliente {

    private List<CartaoAprovado> cartoes;
}
