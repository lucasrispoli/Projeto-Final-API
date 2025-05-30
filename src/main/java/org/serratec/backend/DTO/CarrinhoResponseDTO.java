package org.serratec.backend.DTO;

import jdk.jshell.Snippet;
import org.serratec.backend.enums.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CarrinhoResponseDTO(LocalDateTime aberturaPedido, Integer quantidade, BigDecimal PrecoUnidade, BigDecimal desconto, StatusEnum status) {
}
