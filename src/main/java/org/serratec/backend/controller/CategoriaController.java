package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.DTO.CategoriaRequestDTO;
import org.serratec.backend.DTO.CategoriaResponseDTO;
import org.serratec.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;


    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @GetMapping("/listar/{id}")
    public ResponseEntity<CategoriaResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }


    @PostMapping("/inserir")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponseDTO inserir(@Valid @RequestBody CategoriaRequestDTO categoria) {
        return service.inserir(categoria);
    }


    @PostMapping("/inserir/varios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CategoriaResponseDTO> inserirVarios(@Valid @RequestBody List<CategoriaRequestDTO> categorias) {
        return service.inserirVarios(categorias);
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar (@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoria) {
        return ResponseEntity.ok(service.alterar(id, categoria));
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
