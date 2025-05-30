package org.serratec.backend.DTO;

import java.math.BigDecimal;

public record CarrinhoResponseDTO(Integer quantidade, BigDecimal PrecoUnidade, BigDecimal desconto, PedidoResponseDTO pedidoResponseDTO, ProdutoResponseDTO produtoResponseDTO) {
}
