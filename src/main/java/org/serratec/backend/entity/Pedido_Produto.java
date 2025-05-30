package org.serratec.backend.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido_Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantidade;
	private BigDecimal precoUnidade;
	private BigDecimal desconto;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getPrecoUnidade() {
		return precoUnidade;
	}
	public void setPrecoUnidade(BigDecimal precoUnidade) {
		this.precoUnidade = precoUnidade;
	}
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	
}
