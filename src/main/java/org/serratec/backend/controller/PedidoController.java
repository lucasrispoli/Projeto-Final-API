package org.serratec.backend.controller;

import org.serratec.backend.DTO.PedidoRequestDTO;
import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.DTO.ProdutoPedidoResponseDTO;
import org.serratec.backend.DTO.StatusDTO;
import org.serratec.backend.enums.StatusEnum;
import org.serratec.backend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping("/listar/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }
    @GetMapping("/listarPedidoPorCliente/{id}")
    public ResponseEntity<List<ProdutoPedidoResponseDTO>> buscarPedidoCliente(@PathVariable Long id) {
       return ResponseEntity.ok(service.buscarTodos(id));
    }

    @PostMapping("/inserir")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO inserirPedido(@Valid @RequestBody PedidoRequestDTO pedido) {
        return service.abrirPedido(pedido);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody StatusDTO status){
        return ResponseEntity.ok(service.atualizarStatus(id, status.getStatus()));
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        return service.cancelarPedido(id);
    }
}
