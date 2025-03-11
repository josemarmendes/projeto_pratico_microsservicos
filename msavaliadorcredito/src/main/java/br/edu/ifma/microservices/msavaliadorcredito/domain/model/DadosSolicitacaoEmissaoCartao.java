package br.edu.ifma.microservices.msavaliadorcredito.domain.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DadosSolicitacaoEmissaoCartao {
    private Long idCartao;
    private String cpf;
    private String nomeCliente;
    private String endereco;
    private BigDecimal limiteLiberado;

}