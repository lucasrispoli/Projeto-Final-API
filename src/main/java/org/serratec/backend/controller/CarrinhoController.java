package org.serratec.backend.controller;

import org.serratec.backend.DTO.CarrinhoRequestDTO;
import org.serratec.backend.DTO.CarrinhoResponseDTO;
import org.serratec.backend.entity.Carrinho;
import org.serratec.backend.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @PostMapping("/inserir")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Carrinho> inserirPedido(@Valid @RequestBody CarrinhoRequestDTO pedido) {
        return ResponseEntity.ok(service.InserPedidoProduto(pedido));
    }

    @GetMapping("{id}")
    public ResponseEntity<CarrinhoResponseDTO> finalizarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizarPedido(id));
    }
}
