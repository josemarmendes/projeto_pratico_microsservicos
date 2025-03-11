package br.edu.ifma.microsservices.mscartoes.domain.representation;

import br.edu.ifma.microsservices.mscartoes.domain.BandeiraCartao;
import br.edu.ifma.microsservices.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limite);
    }

}
