package br.edu.ifma.microsservices.mscartoes.domain.representation;

import br.edu.ifma.microsservices.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClienteResponse fromModel(ClienteCartao clienteCartao){
        String nome = clienteCartao.getCartao().getNome();
        String bandeira = clienteCartao.getCartao().getBandeira().toString();
        BigDecimal limiteLiberado = clienteCartao.getLimite();

        return new CartoesPorClienteResponse(nome, bandeira, limiteLiberado);

    }
}
