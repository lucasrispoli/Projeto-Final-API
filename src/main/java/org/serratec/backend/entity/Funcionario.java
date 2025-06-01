package org.serratec.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;

@Entity
public class Funcionario extends Usuario {
	
	
	private String cargo;
	private BigDecimal salario;
	
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		return 	"Nome - " + super.getNome() +
				", email - " + super.getEmail() +
				", cargo - " + cargo +
				", salário - " + salario;
	}
}
