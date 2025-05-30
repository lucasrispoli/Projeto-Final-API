package org.serratec.backend.entity;

import jakarta.persistence.Entity;

@Entity
public class Cliente extends Usuario{

	private String complemento;
	private String cep;

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
