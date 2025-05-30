package org.serratec.backend.DTO;

public record PedidoProdutoResponseDTO(Integer quantidade, Double PrecoUnidade,Double desconto,Long idPedido, Long idProduto, Double valorTotal) {
}
