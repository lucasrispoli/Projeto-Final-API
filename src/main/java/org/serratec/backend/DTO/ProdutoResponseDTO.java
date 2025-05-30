package org.serratec.backend.DTO;

import java.math.BigDecimal;

public record ProdutoResponseDTO(String nome, BigDecimal valor, String categoria) {
}
