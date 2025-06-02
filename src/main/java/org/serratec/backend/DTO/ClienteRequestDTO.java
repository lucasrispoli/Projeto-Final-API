package org.serratec.backend.DTO;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Endereco;

public class ClienteRequestDTO {
	@NotBlank
	private String nome;
	@NotBlank
	private String telefone;
	@Email
	private String email;
	@CPF
	private String cpf;

	private String senha;

	private String complemento;
	private String cep;

	public ClienteRequestDTO() {
	}

	public ClienteRequestDTO(Cliente cliente, String cep) {
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.cpf = cliente.getCpf();
		this.senha = cliente.getCpf();
		this.complemento = cliente.getComplemento();
		this.cep = cep;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

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

	@Override
	public String toString() {
		return "Nome:" + nome + "\n" +
				"Email: " + email + "\n";
	}
}