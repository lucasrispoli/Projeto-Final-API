package org.serratec.backend.DTO;

import org.serratec.backend.enums.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record CarrinhoResponseDTO(LocalDateTime aberturaPedido, StatusEnum status, List<PacoteProdutoResponseDTO> produto, BigDecimal total)  {

    @Override
    public String toString() {
        return "Data do Pedido: " + aberturaPedido.toLocalDate() + "\n" +
                "Hora do Pedido: " + aberturaPedido.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n" +
                "Status: " + status + "\n" +
                "Produto(s): " + produto.toString() + "\n" + "\n" +
                "Total: R$" + total;
    }
}


