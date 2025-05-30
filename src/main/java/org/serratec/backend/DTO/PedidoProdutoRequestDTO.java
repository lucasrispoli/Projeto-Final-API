package org.serratec.backend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Produto;

import java.math.BigDecimal;

public class PedidoProdutoRequestDTO {
    private Integer quantidade;
    @JsonIgnore
    private BigDecimal precoUnidade;
    private BigDecimal desconto;
    private Pedido pedido;
    private Produto produto;

    public PedidoProdutoRequestDTO(Integer quantidade, BigDecimal precoUnidade, BigDecimal desconto, Pedido pedido, Produto produto) {
        this.quantidade = quantidade;
        this.precoUnidade = precoUnidade;
        this.desconto = desconto;
        this.pedido = pedido;
        this.produto = produto;
    }

    public PedidoProdutoRequestDTO() {
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnidade(BigDecimal precoUnidade) {
        this.precoUnidade = precoUnidade;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnidade() {
        return precoUnidade;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Produto getProduto() {
        return produto;
    }
}
