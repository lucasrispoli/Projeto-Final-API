package org.serratec.backend.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErroResposta {
	private Integer status;
	private String titulo;
	private LocalDateTime dataHora;
	private List<String> errors;

	public ErroResposta(Integer status, String titulo, LocalDateTime dataHora, List<String> erros) {
		this.status = status;
		this.titulo = titulo;
		this.dataHora = dataHora;
		this.errors = erros;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public List<String> getErros() {
		return errors;
	}

	public void setErros(List<String> erros) {
		this.errors = erros;
	}

	
	
}