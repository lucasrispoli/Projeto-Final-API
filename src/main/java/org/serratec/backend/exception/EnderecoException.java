package org.serratec.backend.exception;

public class EnderecoException extends RuntimeException {

    //VERIFICA SE O ENDEREÇO FOI FORNECIDO CORRETAMENTE
    public EnderecoException(String message) {
        super(message);
    }
}
