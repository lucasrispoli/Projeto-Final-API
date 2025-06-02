package org.serratec.backend.DTO;

import org.serratec.backend.enums.PlataformaEnum;

import java.math.BigDecimal;

public record ProdutoResponseDTO(String nome, BigDecimal valor, String categoria, PlataformaEnum plataforma) {
}
