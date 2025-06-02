package org.serratec.backend.DTO;

import java.math.BigDecimal;

import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Produto;

import jakarta.validation.constraints.Min;
import org.serratec.backend.enums.PlataformaEnum;

public class ProdutoRequestDTO {


    private String nome;

    @Min(0)
    private BigDecimal valor;

    private Categoria categoria;

    private PlataformaEnum plataforma;

    public ProdutoRequestDTO() {
    }

    public ProdutoRequestDTO(Produto produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.categoria = produto.getCategoria();
        this.plataforma = produto.getPlataforma();
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

    public PlataformaEnum getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(PlataformaEnum plataforma) {
        this.plataforma = plataforma;
    }
}
