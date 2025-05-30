package org.serratec.backend.DTO;

import org.serratec.backend.enums.StatusEnum;

import java.time.LocalDateTime;

public record PedidoResponseDTO(Long id, LocalDateTime dateEmissao, StatusEnum statusEnum) {
}
