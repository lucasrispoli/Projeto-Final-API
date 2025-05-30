package org.serratec.backend.DTO;

import java.math.BigDecimal;

public record FuncionarioResponseDTO(String nome, String telefone, String email, BigDecimal salario) {
}
