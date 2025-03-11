package br.edu.ifma.microsservices.mscartoes.domain;

import lombok.Data;
import java.math.BigDecimal;
@Data
public class DadosSolicitacaoEmissaoCartao {
    private Long idCartao;
    private String nomeCliente;
    private String cpf;
    private String endereco;
    private BigDecimal limiteLiberado;

}