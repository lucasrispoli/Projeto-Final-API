package org.serratec.backend.DTO;

import java.math.BigDecimal;

import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Produto;

import jakarta.validation.constraints.Min;

public class ProdutoRequestDTO {


    private String nome;

    @Min(0)
    private BigDecimal valor;

    private Categoria categoria;

    public ProdutoRequestDTO() {
    }

    public ProdutoRequestDTO(Produto produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.categoria = produto.getCategoria();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
