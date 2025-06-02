package org.serratec.backend.DTO;

import org.serratec.backend.enums.CargoEnum;

import java.math.BigDecimal;

public record FuncionarioResponseDTO(String nome, String telefone, String email, CargoEnum cargo, BigDecimal salario) {
}
