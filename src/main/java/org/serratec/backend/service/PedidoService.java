package org.serratec.backend.service;

import org.serratec.backend.DTO.PedidoRequestDTO;
import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.enums.StatusEnum;
import org.serratec.backend.exception.PedidoException;
import org.serratec.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ClienteService cService;

	
	public PedidoResponseDTO InserirPedido(PedidoRequestDTO pedidoResponseDTO) {
		Pedido pedidoEntity = new Pedido();
		System.out.println(pedidoResponseDTO.getDataPedido());
		Cliente cliente = cService.buscarClientePorID(pedidoResponseDTO.getCliente().getId());
		pedidoEntity.setStatus(pedidoResponseDTO.getStatus());
		pedidoEntity.setCliente(cliente);
		pedidoEntity = repository.save(pedidoEntity);
		return new PedidoResponseDTO(pedidoEntity.getId(),pedidoEntity.getDataPedido(), pedidoEntity.getStatus());
	}

	public Pedido listarPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if(!pedido.isPresent()){
			throw new PedidoException("Pedido não encontrado!");
		}
		return pedido.get();
	}

	public Pedido buscarPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if (!pedido.isPresent()) {
			throw new PedidoException("Pedido não encontrado!");
		}
		return pedido.get();
	}

}
