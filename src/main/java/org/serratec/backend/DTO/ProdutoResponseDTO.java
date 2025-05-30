package org.serratec.backend.DTO;

import org.serratec.backend.entity.Categoria;

import java.math.BigDecimal;

public record ProdutoResponseDTO(String nome, BigDecimal valor, Categoria categoria) {
}
