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

//    O correto é um desse bloco antes de cada endpoint
//    @Operation(summary = "Lista todos os clientes", description = "A resposta lista os dados dos clientes id, nome, cpf e email.")
//    @ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json") }, description = "Retorna todos os clientes"),
//            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
//            @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
//            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
//            @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponseDTO inserir(@Valid @RequestBody CategoriaRequestDTO categoria) {
        return service.inserir(categoria);
    }


    @PostMapping("/varios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CategoriaResponseDTO> inserirVarios(@Valid @RequestBody List<CategoriaRequestDTO> categorias) {
        return service.inserirVarios(categorias);
    }


    @PutMapping("{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar (@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoria) {
        return ResponseEntity.ok(service.alterar(id, categoria));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
