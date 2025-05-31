package org.serratec.backend.service;

import org.serratec.backend.DTO.*;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.repository.CarrinhoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        Optional<Produto> produto = repositoryProduto.findById(carrinhoDTO.getProduto().getId());
        carrinho.setQuantidade(carrinhoDTO.getQuantidade());
        carrinho.setPrecoUnidade(produto.get().getValor());
        carrinho.setDesconto(carrinhoDTO.getDesconto());
        carrinho.setPedido(pedido);
        carrinho.setProduto(produto.get());
        carrinho = repository.save(carrinho);

        return carrinho;
    }


    public CarrinhoResponseDTO finalizarPedido(Long id) {
        List<Carrinho> carrinho = repository.carregarPedidos(id);
        List<PacoteProdutoResponseDTO> produtosDTO = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        Pedido pedido = carrinho.get(0).getPedido();
        Produto produto;
        for (Carrinho item : carrinho) {
            produto = item.getProduto();
            produtosDTO.add(new PacoteProdutoResponseDTO(produto.getNome(), produto.getValor(), produto.getCategoria().getNome(),
                            item.getQuantidade(), item.getDesconto()));
                            total = total.add(produto.getValor().multiply(new BigDecimal(item.getQuantidade())));
        }
        return new CarrinhoResponseDTO(pedido.getDataPedido(), pedido.getStatus(), produtosDTO, total);
    }

    public ResponseEntity<Carrinho> removerPorId(Long id) {
        Optional<Carrinho> item = repository.findById(id);
        if(item.isPresent()){
            Carrinho carrinho = item.get();
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
