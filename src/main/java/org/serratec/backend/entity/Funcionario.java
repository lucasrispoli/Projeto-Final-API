package org.serratec.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.serratec.backend.enums.CargoEnum;

@Entity
public class Funcionario extends Usuario {
	
	@Enumerated(EnumType.STRING)
	private CargoEnum cargo;
	private BigDecimal salario;

	public CargoEnum getCargo() {
		return cargo;
	}

	public void setCargo(CargoEnum cargo) {
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
