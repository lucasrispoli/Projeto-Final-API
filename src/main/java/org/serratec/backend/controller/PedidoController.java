package org.serratec.backend.controller;

import org.serratec.backend.DTO.PedidoRequestDTO;
import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/inserir")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoResponseDTO> inserirPedido(@RequestBody PedidoRequestDTO pedido) {
        return ResponseEntity.ok(pedidoService.InserirPedido(pedido));
    }

    @GetMapping("{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.listarPorId(id));
    }
}
