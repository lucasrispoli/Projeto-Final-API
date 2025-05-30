package org.serratec.backend.DTO;

import java.math.BigDecimal;

public record PacoteProdutoResponseDTO(String nome, BigDecimal valor, String categoria, Integer quantidade, BigDecimal desconto) {
}
