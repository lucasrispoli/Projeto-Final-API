package org.serratec.backend.controller;

import org.serratec.backend.DTO.CarrinhoRequestDTO;
import org.serratec.backend.DTO.CarrinhoResponseDTO;
import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @PostMapping("/inserir")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Carrinho> inserirPedido(@RequestBody CarrinhoRequestDTO pedido) {
        return ResponseEntity.ok(service.InserPedidoProduto(pedido));
    }

    @GetMapping("{id}")
    public ResponseEntity<CarrinhoResponseDTO> finalizarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizarPedido(id));
    }

    @DeleteMapping("/remover/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Carrinho> remover(@PathVariable Long id){
        return service.removerPorId(id);
    }



}
