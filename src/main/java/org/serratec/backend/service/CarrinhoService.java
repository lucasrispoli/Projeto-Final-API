package org.serratec.backend.service;

import org.serratec.backend.DTO.*;
import org.serratec.backend.config.MailConfig;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.enums.StatusEnum;
import org.serratec.backend.exception.CarrinhoException;
import org.serratec.backend.exception.PedidoException;
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
            throw new PedidoException("Pedido não encontrado com o ID: " + carrinhoDTO.getPedido().getId());
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

        if (carrinho.isEmpty()) {
            throw new CarrinhoException("Carrinho vazio");
        }

        Pedido pedido = carrinho.get(0).getPedido();

        for (Carrinho item : carrinho) {
            Produto produto = item.getProduto();
            BigDecimal valorBruto = produto.getValor().multiply(BigDecimal.valueOf(item.getQuantidade()));
            BigDecimal valorDesconto = valorBruto.multiply(item.getDesconto());
            BigDecimal valorFinal = valorBruto.subtract(valorDesconto);

            produtosDTO.add(new PacoteProdutoResponseDTO(
                    produto.getNome(),
                    produto.getValor(),
                    produto.getCategoria().getNome(),
                    item.getQuantidade(),
                    item.getDesconto()
            ));

            desconto = desconto.add(valorDesconto);
            total = total.add(valorFinal);
        }

        pedido.setStatus(StatusEnum.PAGO);
        pedidoService.atualizarStatus(pedido.getId(), pedido.getStatus());

        CarrinhoResponseDTO carrinhoDTO = new CarrinhoResponseDTO(
                pedido.getDataPedido(),
                pedido.getStatus(),
                produtosDTO,
                total
        );

        mailConfig.enviar(
                pedido.getCliente().getEmail(),
                "Pedido realizado com sucesso",
                pedido.getCliente().getNome(),
                "Itens:",
                carrinhoDTO.toString(),
                "CompraTemplate"
        );

        return carrinhoDTO;
    }

    public ResponseEntity<Carrinho> removerPorId(Long id) {
        Optional<Carrinho> item = repository.findById(id);
        if(item.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new CarrinhoException("Carrinho não encontrado");
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
