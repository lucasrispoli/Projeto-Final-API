package org.serratec.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.serratec.backend.enums.StatusEnum;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime dataPedido;
	
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@OneToMany(mappedBy = "pedido")
	@JsonManagedReference
	private List<Pedido_Produto> itens;
	
	
	

}
