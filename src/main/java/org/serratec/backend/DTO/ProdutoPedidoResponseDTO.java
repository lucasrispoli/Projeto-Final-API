package org.serratec.backend.DTO;

import org.serratec.backend.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public record ProdutoPedidoResponseDTO(Long numeroPedido, LocalDateTime dataDeEmissao, StatusEnum status, List<ProdutoResponseDTO> produto) {
}
