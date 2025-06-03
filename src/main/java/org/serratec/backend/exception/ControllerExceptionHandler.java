package org.serratec.backend.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.entity.Pedido;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> erros = new ArrayList<>();

		for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
			erros.add(erro.getField() + ":" + erro.getDefaultMessage());
		}
		ErroResposta erroResposta = new ErroResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(),
				erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(),
				erros);
		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);

	}


	@ExceptionHandler(ClienteException.class)
	protected ResponseEntity<Object> handleClienteException(ClienteException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro no cliente",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(CategoriaException.class)
	protected ResponseEntity<Object> handleClienteException(CategoriaException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro na categoria",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(CarrinhoException.class)
	protected ResponseEntity<Object> handleClienteException(CarrinhoException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro no carrinho",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(EnderecoException.class)
	protected ResponseEntity<Object> handleClienteException(EnderecoException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro no endereco",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(FuncionarioException.class)
	protected ResponseEntity<Object> handleClienteException(FuncionarioException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro no funcionário",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(PedidoException.class)
	protected ResponseEntity<Object> handleClienteException(PedidoException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro no pedido",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(PerfilException.class)
	protected ResponseEntity<Object> handleClienteException(PerfilException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro no perfil",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(ProdutoException.class)
	protected ResponseEntity<Object> handleClienteException(ProdutoException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro no produto",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EnumException.class)
	protected ResponseEntity<Object> handleClienteException(EnumException ex) {
		List<String> erros = List.of(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Erro no enum utilizado",
				LocalDateTime.now(), erros);
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

}