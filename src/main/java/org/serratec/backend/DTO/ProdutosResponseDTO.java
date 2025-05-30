package org.serratec.backend.DTO;

import org.serratec.backend.entity.Categoria;
import org.serratec.backend.enums.CategoriaEnum;

import java.math.BigDecimal;

public record ProdutosResponseDTO(String nome, BigDecimal valor, String categoria, Integer quantidade, BigDecimal desconto) {
}
