package org.serratec.backend.service;

import org.serratec.backend.DTO.*;
import org.serratec.backend.config.MailConfig;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.enums.StatusEnum;
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

	//ABRE UM PEDIDO PARA UM CLIENTE
	public PedidoResponseDTO abrirPedido(PedidoRequestDTO pedidoRequestDTO) {
		Pedido pedidoEntity = new Pedido();
		Cliente cliente = cService.buscarClientePorID(pedidoRequestDTO.getCliente().getId());
//		pedidoEntity.setStatus(pedidoRequestDTO.getStatus());
		pedidoEntity.setStatus(StatusEnum.ABERTO);
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

	//LISTAR PEDIDOS POR ID DO CLIENTE
	public List<ProdutoPedidoResponseDTO> buscarTodos(Long idCliente) {
	List<Pedido> pedidos = repository.listarPedidosPorIdUsuario(idCliente);
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

	//BUSCA UM PEDIDO POR ID PARA O CARRINHO
	public Pedido buscarPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if (!pedido.isPresent()) {
			throw new PedidoException("Pedido não encontrado!");
		}
		return pedido.get();
	}

	//ATUALIZA O STATUS DO PEDIDO
	public PedidoResponseDTO atualizarStatus(Long id, StatusEnum status) {
		var pedido = buscarPorId(id);
		System.out.println(status);
		pedido.setStatus(status);
		System.out.println(pedido.toString());
		pedido = repository.save(pedido);
		mailConfig.enviar(pedido.getCliente().getEmail(), "Confirmação de Cadastro do Funcionário", pedido.getCliente().getNome(), "Funcionário:", pedido.toString());
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
