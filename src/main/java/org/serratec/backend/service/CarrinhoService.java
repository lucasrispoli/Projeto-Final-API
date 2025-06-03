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
        carrinho.setDesconto(descontar(carrinho.getQuantidade(),carrinhoDTO.getDesconto()));
        carrinho.setPedido(pedido);
        carrinho.setProduto(produto);

        return repository.save(carrinho);
    }


    public CarrinhoResponseDTO finalizarPedido(Long id) {
        List<Carrinho> carrinho = repository.carregarPedidos(id);
        List<PacoteProdutoResponseDTO> produtosDTO = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal desconto = BigDecimal.ZERO;
        Pedido pedido = carrinho.get(0).getPedido();
        Produto produto;
        for (Carrinho item : carrinho) {
            produto = item.getProduto();
            produtosDTO.add(new PacoteProdutoResponseDTO(produto.getNome(), produto.getValor(), produto.getCategoria().getNome(),
                            item.getQuantidade(), item.getDesconto()));
                            desconto = desconto.add((produto.getValor().multiply(new BigDecimal(item.getQuantidade()))).multiply(item.getDesconto()));
                            total = total.add((produto.getValor().multiply(new BigDecimal(item.getQuantidade()))).subtract(desconto)); //Desconta em cada unidade
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


    private BigDecimal descontar(Integer quantidade, BigDecimal desconto) {
        if (quantidade>1) {
            return BigDecimal.valueOf(0.05);
        } else if (quantidade >= 3) {
            return BigDecimal.valueOf(0.1);
        }
        return BigDecimal.valueOf(0);
    }

}
