package org.serratec.backend.service;

import org.serratec.backend.DTO.*;
import org.serratec.backend.config.MailConfig;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.enums.StatusEnum;
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

    @Autowired
    private MailConfig mailConfig;

    public Carrinho inserirPedidoProduto(CarrinhoRequestDTO carrinhoDTO) {
        Pedido pedido = pedidoService.buscarPorId(carrinhoDTO.getPedido().getId());
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + carrinhoDTO.getPedido().getId());
        }

        Produto produto = repositoryProduto.findById(carrinhoDTO.getProduto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + carrinhoDTO.getProduto().getId()));

        Carrinho carrinho = new Carrinho();
        carrinho.setQuantidade(carrinhoDTO.getQuantidade());
        carrinho.setPrecoUnidade(produto.getValor());
        carrinho.setDesconto(carrinhoDTO.getDesconto());
        carrinho.setPedido(pedido);
        carrinho.setProduto(produto);

        return repository.save(carrinho);
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
//                            total = total.add((produto.getValor().subtract(item.getDesconto())).multiply(new BigDecimal(item.getQuantidade()))); //Desconta em cada unidade
                            total = total.add(produto.getValor().multiply(new BigDecimal(item.getQuantidade())).subtract(item.getDesconto())); //Desconta no total
        }
        pedido.setStatus(StatusEnum.PAGO); //Acho que na finalização do produto o correto seria trocar seu status.
        pedidoService.atualizarStatus(pedido.getId(), pedido.getStatus());

        CarrinhoResponseDTO carrinhoDTO = new CarrinhoResponseDTO(pedido.getDataPedido(), pedido.getStatus(), produtosDTO, total);

        mailConfig.enviar(pedido.getCliente().getEmail(), "Pedido realizado com sucesso", pedido.getCliente().getNome(),"Itens:", carrinhoDTO.toString());

        return carrinhoDTO;
    }

    public ResponseEntity<Carrinho> removerPorId(Long id) {
        Optional<Carrinho> item = repository.findById(id);
        if(item.isPresent()){
//            Carrinho carrinho = item.get(); //Pq?
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

//    public List<CarrinhoResponseDTO> buscarItensPorIdCliente(Long idCliente) {
//        List<Carrinho> carrinhos = repository.buscarItensPorIdPedido(idCliente);
//        if (carrinhos.isEmpty()) {
//            throw new IllegalArgumentException("Nenhum item encontrado para o cliente com ID: " + idCliente);
//        }
//
//        return null;
//    }
}
