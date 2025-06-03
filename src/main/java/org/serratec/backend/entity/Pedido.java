package org.serratec.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.enums.StatusEnum;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime dataPedido;
	
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	@OneToMany(mappedBy = "pedido")
	@JsonManagedReference(value = "pedido-itens")
	private List<Carrinho> itens;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public void setItens(List<Carrinho> itens) {
		this.itens = itens;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public List<Carrinho> getItens() {
		return itens;
	}
}
