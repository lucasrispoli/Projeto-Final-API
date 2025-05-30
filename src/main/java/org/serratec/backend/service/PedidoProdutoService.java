package org.serratec.backend.service;

import org.serratec.backend.DTO.PedidoProdutoRequestDTO;
import org.serratec.backend.DTO.PedidoRequestDTO;
import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Pedido_Produto;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.repository.Pedido_ProdutoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoProdutoService {
    
    @Autowired
    private Pedido_ProdutoRepository repository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoRepository repositoryProduto;

    public Pedido_Produto InserPedidoProduto(PedidoProdutoRequestDTO pedidoResponseDTO) {
        Pedido_Produto ppEntity = new Pedido_Produto();
        Pedido pedido = pedidoService.buscarPorId(pedidoResponseDTO.getPedido().getId());
        Optional<Produto> produto= repositoryProduto.findById(pedidoResponseDTO.getProduto().getId());
        ppEntity.setQuantidade(pedidoResponseDTO.getQuantidade());
        ppEntity.setPrecoUnidade(produto.get().getValor());
        ppEntity.setDesconto(pedidoResponseDTO.getDesconto());
        ppEntity.setPedido(pedido);
        ppEntity.setProduto(produto.get());
        ppEntity = repository.save(ppEntity);
        return ppEntity;
    }
//
}
