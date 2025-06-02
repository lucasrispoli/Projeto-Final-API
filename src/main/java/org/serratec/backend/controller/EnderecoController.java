package org.serratec.backend.controller;

import org.serratec.backend.DTO.EnderecoResponseDTO;
import org.serratec.backend.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @GetMapping("/inserir/{cep}")
    public ResponseEntity<EnderecoResponseDTO> buscarCep(@PathVariable String cep) {
        var endereco = service.buscar(cep);
        if( endereco != null){
            return ResponseEntity.ok(endereco);
        }
        return ResponseEntity.notFound().build();
    }
}
