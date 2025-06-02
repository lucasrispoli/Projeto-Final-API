package org.serratec.backend.DTO;

import java.math.BigDecimal;

public record PacoteProdutoResponseDTO(String nome, BigDecimal valor, String categoria, Integer quantidade, BigDecimal desconto) {

    @Override
    public String toString() {
        return nome + "\n" +
                "Valor:" + valor + "\n" +
                "Categoria: " + categoria + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Desconto: " + desconto;
    }
}
