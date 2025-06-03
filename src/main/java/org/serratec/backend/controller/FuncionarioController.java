package org.serratec.backend.controller;

import jakarta.validation.Valid;
import org.serratec.backend.DTO.FuncionarioRequestDTO;
import org.serratec.backend.DTO.FuncionarioResponseDTO;
import org.serratec.backend.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;


    @GetMapping("/listar")
    public ResponseEntity<List<FuncionarioResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @GetMapping("/listar/{id}")
    public ResponseEntity<FuncionarioResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }


    @GetMapping("/listar/inativos")
    public ResponseEntity<List<FuncionarioResponseDTO>> listarInativos() {
        return ResponseEntity.ok(service.listarFuncionariosInativos());
    }


    @PostMapping("/inserir")
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponseDTO inserir(@Valid @RequestBody FuncionarioRequestDTO funcionario) {
        return service.inserir(funcionario);
    }


    @PostMapping("/inserir/varios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FuncionarioResponseDTO> inserirVarios(@Valid @RequestBody List<FuncionarioRequestDTO> funcionarios) {
        return service.inserirVarios(funcionarios);
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizar (@PathVariable Long id, @Valid @RequestBody FuncionarioRequestDTO funcionario) {
        return ResponseEntity.ok(service.alterar(id, funcionario));
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
