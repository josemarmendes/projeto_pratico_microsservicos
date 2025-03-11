package br.edu.ifma.microservices.msavaliadorcredito.exception;

public class ErroSolicitacaoCartaoException extends RuntimeException {

    public ErroSolicitacaoCartaoException(String mensagem) {
        super(mensagem);
    }
}
