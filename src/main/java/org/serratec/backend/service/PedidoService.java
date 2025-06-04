package org.serratec.backend.service;

import org.serratec.backend.DTO.*;
import org.serratec.backend.config.MailConfig;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.enums.StatusEnum;
import org.serratec.backend.exception.EnumException;
import org.serratec.backend.exception.PedidoException;
import org.serratec.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ClienteService cService;

	@Autowired
	private MailConfig mailConfig;

	public PedidoResponseDTO abrirPedido(PedidoRequestDTO pedidoRequestDTO) {
		Pedido pedidoEntity = new Pedido();
		Cliente cliente = cService.buscarClientePorID(pedidoRequestDTO.getCliente().getId());
		pedidoEntity.setStatus(StatusEnum.ABERTO);
		pedidoEntity.setCliente(cliente);
		pedidoEntity = repository.save(pedidoEntity);

		return new PedidoResponseDTO(pedidoEntity.getId(),pedidoEntity.getDataPedido(), pedidoEntity.getStatus());
	}

	public PedidoResponseDTO listarPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if(!pedido.isPresent()){
			throw new PedidoException("Pedido não encontrado!");
		}

		return new PedidoResponseDTO(pedido.get().getId(),pedido.get().getDataPedido(),pedido.get().getStatus());
	}

	public List<ProdutoPedidoResponseDTO> buscarTodos(Long idCliente) {
	List<Pedido> pedidos = repository.listarPedidosPorIdUsuario(idCliente);

	if (pedidos.isEmpty()) {
		throw new PedidoException("Usuário não possui pedidos cadastrados");
	}

	List<ProdutoPedidoResponseDTO> pedidosDTO = new ArrayList<>();

		for (Pedido pedido : pedidos) {
			List<ProdutoResponseDTO> produtosDTO = new ArrayList<>();
			List<Produto> produtos = repository.obterProdutosPorPedido(pedido.getId());

			for (Produto produto : produtos) {
					produtosDTO.add(new ProdutoResponseDTO(produto.getNome(),produto.getValor(), produto.getCategoria().getNome(), produto.getPlataforma()));
			}
			pedidosDTO.add(new ProdutoPedidoResponseDTO(
					pedido.getId(),
					pedido.getDataPedido(),
					pedido.getStatus(), produtosDTO));

		}
		return pedidosDTO;
	}

	public Pedido buscarPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if (!pedido.isPresent()) {
			throw new PedidoException("Pedido não encontrado!");
		}
		return pedido.get();
	}

	public PedidoResponseDTO atualizarStatus(Long id, StatusEnum status) {
		var pedido = buscarPorId(id);
		if (status != StatusEnum.ENVIADO && status != StatusEnum.ENTREGUE && status !=StatusEnum.PAGO) {
			throw new EnumException("Status inválido!");
		}
		pedido.setStatus(status);
		pedido = repository.save(pedido);
		PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO(pedido.getId(), pedido.getDataPedido(), pedido.getStatus());
		mailConfig.enviar(pedido.getCliente().getEmail(), "Atualização pedido", pedido.getCliente().getNome(),
				"Pedido:", pedidoResponseDTO.toString(), "CompraTemplate");
		return pedidoResponseDTO;
	}

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
