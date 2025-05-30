package org.serratec.backend.controller;

import org.serratec.backend.DTO.ProdutoRequestDTO;
import org.serratec.backend.DTO.ProdutoResponseDTO;
import org.serratec.backend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO inserir(@RequestBody ProdutoRequestDTO produto) {
        return service.inserir(produto);
    }


    @PostMapping("/varios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProdutoResponseDTO> inserirVarios(@RequestBody List<ProdutoRequestDTO> produtos) {
        return service.inserirVarios(produtos);
    }


    @PutMapping("{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar (@PathVariable Long id, @RequestBody ProdutoRequestDTO produto) {
        return ResponseEntity.ok(service.alterar(id, produto));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
