package org.serratec.backend.controller;

import org.serratec.backend.DTO.ClienteRequestDTO;
import org.serratec.backend.DTO.ClienteResponseDTO;
import org.serratec.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return service.listarClientes();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<ClienteResponseDTO> listar(@PathVariable Long id) {
        return service.listarPorId(id);
    }

    @GetMapping("/listar/deletados")
    public ResponseEntity<List<ClienteResponseDTO>> listarDeletados() {
        return service.listarClientesDeletados();
    }

   @PostMapping("/inserir")
   @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO inserir(@RequestBody ClienteRequestDTO cliente) {
       return service.salvarCliente(cliente);
   }

   @PutMapping("/atualizar/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO cliente) {
       return ResponseEntity.ok(service.atualizarCliente(id, cliente));
   }

   @DeleteMapping("deletar/{id}")
   public ResponseEntity<Void> remover(@PathVariable Long id) {
       service.deletar(id);
       return ResponseEntity.noContent().build();
   }

}
