package org.serratec.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class AvaliacaoProduto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotEmpty(message = "Informe uma descricao para a avalição do produto")
	@Column(nullable = false)
	private String descricao;

	@Min(value = 1, message = "A nota deve ser pelo menos 1")
	@Max(value = 10, message = "A nota deve ser pelo menos 10")
	@Column(nullable = false)
	private Integer nota;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "id_usuario"))
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "id_produto"))
	private Produto produto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
