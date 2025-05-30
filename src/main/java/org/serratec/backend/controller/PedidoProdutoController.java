package org.serratec.backend.controller;

import org.serratec.backend.DTO.PedidoProdutoRequestDTO;
import org.serratec.backend.DTO.PedidoRequestDTO;
import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.entity.Pedido_Produto;
import org.serratec.backend.service.PedidoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidosProdutos")
public class PedidoProdutoController {

    @Autowired
    private PedidoProdutoService service;

    @PostMapping("/inserir")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pedido_Produto> inserirPedido(@RequestBody PedidoProdutoRequestDTO pedido) {
        return ResponseEntity.ok(service.InserPedidoProduto(pedido));
    }
}
