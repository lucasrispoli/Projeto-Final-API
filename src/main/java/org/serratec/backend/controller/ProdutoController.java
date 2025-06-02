package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.DTO.ProdutoRequestDTO;
import org.serratec.backend.DTO.ProdutoResponseDTO;
import org.serratec.backend.service.ProdutoService;
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
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

//    O correto é um desse bloco antes de cada endpoint
//    @Operation(summary = "Lista todos os clientes", description = "A resposta lista os dados dos clientes id, nome, cpf e email.")
//    @ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = Produto.class), mediaType = "application/json") }, description = "Retorna todos os clientes"),
//            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
//            @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
//            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
//            @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })

    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @PostMapping("/inserir")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO inserir(@Valid @RequestBody ProdutoRequestDTO produto) {
        return service.inserir(produto);
    }

    @PostMapping("/inserir/varios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProdutoResponseDTO> inserirVarios(@Valid @RequestBody List<ProdutoRequestDTO> produtos) {
        return service.inserirVarios(produtos);
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar (@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produto) {
        return ResponseEntity.ok(service.alterar(id, produto));
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
