package org.serratec.backend.service;

import org.serratec.backend.DTO.CarrinhoRequestDTO;
import org.serratec.backend.DTO.CarrinhoResponseDTO;
import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.DTO.ProdutoResponseDTO;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Carrinho;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.repository.CarrinhoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoService {
    
    @Autowired
    private CarrinhoRepository repository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoRepository repositoryProduto;

    public Carrinho InserPedidoProduto(CarrinhoRequestDTO carrinhoDTO) {
        Carrinho carrinho = new Carrinho();
        Pedido pedido = pedidoService.buscarPorId(carrinhoDTO.getPedido().getId());
        Optional<Produto> produto= repositoryProduto.findById(carrinhoDTO.getProduto().getId());
        carrinho.setQuantidade(carrinhoDTO.getQuantidade());
        carrinho.setPrecoUnidade(produto.get().getValor());
        carrinho.setDesconto(carrinhoDTO.getDesconto());
        carrinho.setPedido(pedido);
        carrinho.setProduto(produto.get());
        carrinho = repository.save(carrinho);
        return carrinho;
    }


    public List<CarrinhoResponseDTO> finalizarPedido(Long id) {
        List<Carrinho> carrinho = repository.carregarPedidos(id);
        List<CarrinhoResponseDTO> carrinhoDTO = new ArrayList<>();
        for (Carrinho item : carrinho) {
            Pedido pedido = item.getPedido();
            Produto produto = item.getProduto();
            carrinhoDTO.add(new CarrinhoResponseDTO(item.getQuantidade(), item.getPrecoUnidade(), item.getDesconto(),
                new PedidoResponseDTO(pedido.getId(), pedido.getDataPedido(), pedido.getStatus()),
                new ProdutoResponseDTO( produto.getNome(), produto.getValor(), produto.getCategoria())));
        }
        return carrinhoDTO;
    }
//
}
