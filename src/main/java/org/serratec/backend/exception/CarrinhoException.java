package org.serratec.backend.exception;

import org.serratec.backend.entity.PK.Carrinho;

public class CarrinhoException extends RuntimeException {
    public CarrinhoException (String message) {
        super(message);
    }
}
