package org.serratec.backend.exception;

public class ErroConexaoBancoDeDadosException extends RuntimeException {

    public ErroConexaoBancoDeDadosException(String mensagem) {
        super(mensagem);
    }
}