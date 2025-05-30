package org.serratec.backend.DTO;

import org.serratec.backend.enums.StatusEnum;

import java.time.LocalDateTime;

public record PedidoResponseDTO(Long NumeroPedido, LocalDateTime dataDeEmissao, StatusEnum status) {
}
