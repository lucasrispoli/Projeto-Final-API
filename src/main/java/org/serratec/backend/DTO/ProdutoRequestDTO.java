package org.serratec.backend.DTO;

import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Produto;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoRequestDTO {


    private String nome;

//    Tem que pôr para ser maior que zero
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
