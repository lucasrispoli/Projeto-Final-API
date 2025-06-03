package org.serratec.backend.DTO;

import java.math.BigDecimal;

import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Produto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CarrinhoRequestDTO {
    private Integer quantidade;
    @JsonIgnore
    @Min(0)
    private BigDecimal precoUnidade;
    private BigDecimal desconto;
    @NotBlank
    private Pedido pedido;
    @NotBlank
    private Produto produto;

    public CarrinhoRequestDTO(Integer quantidade, BigDecimal precoUnidade, BigDecimal desconto, Pedido pedido, Produto produto) {
        this.quantidade = quantidade;
        this.precoUnidade = precoUnidade;
        this.desconto = desconto;
        this.pedido = pedido;
        this.produto = produto;
    }

    public CarrinhoRequestDTO() {
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
