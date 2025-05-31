package org.serratec.backend.service;

import org.serratec.backend.DTO.PedidoRequestDTO;
import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.DTO.AtualizaStatusDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.enums.StatusEnum;
import org.serratec.backend.exception.PedidoException;
import org.serratec.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ClienteService cService;

	//ABRE UM PEDIDO PARA UM CLIENTE
	public PedidoResponseDTO abrirPedido(PedidoRequestDTO pedidoResponseDTO) {
		Pedido pedidoEntity = new Pedido();
		System.out.println(pedidoResponseDTO.getDataPedido());
		Cliente cliente = cService.buscarClientePorID(pedidoResponseDTO.getCliente().getId());
		pedidoEntity.setStatus(pedidoResponseDTO.getStatus());
		pedidoEntity.setCliente(cliente);
		pedidoEntity = repository.save(pedidoEntity);
		return new PedidoResponseDTO(pedidoEntity.getId(),pedidoEntity.getDataPedido(), pedidoEntity.getStatus());
	}

	//LISTA UM PEDIDO POR ID
	public PedidoResponseDTO listarPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if(!pedido.isPresent()){
			throw new PedidoException("Pedido não encontrado!");
		}
		return new PedidoResponseDTO(pedido.get().getId(),pedido.get().getDataPedido(),pedido.get().getStatus());
	}

	//LISTAR TODOS OS PEDIDOS DO CLIENTE
	public ResponseEntity<List<PedidoResponseDTO>> listarPedidos(Long IdCliente) {
		List<Pedido> pedidos = repository.listarPedidosPorIdUsuario(IdCliente);
		if(pedidos.isEmpty()){
			throw new PedidoException("Nenhum pedido encontrado para o cliente com ID: " + IdCliente);
		}
		return ResponseEntity.ok().body(
				pedidos.stream()
						.map(pedido -> new PedidoResponseDTO(pedido.getId(), pedido.getDataPedido(), pedido.getStatus()))
						.toList()
		);
	}

	//BUSCA UM PEDIDO POR ID PARA O CARRINHO
	public Pedido buscarPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if (!pedido.isPresent()) {
			throw new PedidoException("Pedido não encontrado!");
		}
		return pedido.get();
	}

	//ATUALIZA O STATUS DO PEDIDO
	public PedidoResponseDTO atualizarStatus(Long id, AtualizaStatusDTO status) {
		var pedido = buscarPorId(id);
		pedido.setStatus(status.getStatus());
		pedido = repository.save(pedido);
		return new PedidoResponseDTO(pedido.getId(), pedido.getDataPedido(), pedido.getStatus());
	}

	//CANCELA UM PEDIDO
	public ResponseEntity<Void> cancelarPedido(Long id) {
		var pedido = buscarPorId(id);
		if (pedido.getStatus().equals(StatusEnum.CANCELADO)) {
			throw new PedidoException("Pedido já cancelado!");
		}
		pedido.setStatus(StatusEnum.CANCELADO);
		repository.save(pedido);
		return ResponseEntity.noContent().build();
	}

}
