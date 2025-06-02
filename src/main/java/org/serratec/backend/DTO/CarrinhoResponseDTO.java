package org.serratec.backend.DTO;

import org.serratec.backend.enums.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CarrinhoResponseDTO(LocalDateTime aberturaPedido, StatusEnum status, List<PacoteProdutoResponseDTO> produto, BigDecimal total)  {

    @Override
    public String toString() {
        return "Data do Pedido: " + aberturaPedido + "\n" +
                "Status: " + status + "\n" +
                "Produto: " + produto.toString() + "\n" +
                "Total: " + total;
    }
}


