package org.serratec.backend.exception;

public class ClienteException extends RuntimeException {
 //caso o cpf do cliente ja esteja cadastrado
  public ClienteException(String message) {
    super(message);
  }
  //caso nao ache o cliente:
public ClienteException(String message, Throwable cause) {
    super(message, cause);
}
}
