package org.serratec.backend.DTO;

import org.serratec.backend.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(Long numeroPedido, LocalDateTime dataDeEmissao, StatusEnum status) {

    @Override
    public String toString() {
        return "NumeroPedido: " + numeroPedido + "\n" +
                "Data do Pedido: " + dataDeEmissao.toLocalDate() + "\n" +
                "Status: " + status;
    }
}
