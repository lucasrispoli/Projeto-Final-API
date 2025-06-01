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

//    O correto é um desse bloco antes de cada endpoint
//    @Operation(summary = "Lista todos os clientes", description = "A resposta lista os dados dos clientes id, nome, cpf e email.")
//    @ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = Funcionario.class), mediaType = "application/json") }, description = "Retorna todos os clientes"),
//            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
//            @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
//            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
//            @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @GetMapping("{id}")
    public ResponseEntity<FuncionarioResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }


    @GetMapping("/inativos")
    public ResponseEntity<List<FuncionarioResponseDTO>> listarInativos() {
        return ResponseEntity.ok(service.listarFuncionariosInativos());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponseDTO inserir(@Valid @RequestBody FuncionarioRequestDTO funcionario) {
        return service.inserir(funcionario);
    }


    @PostMapping("/varios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FuncionarioResponseDTO> inserirVarios(@Valid @RequestBody List<FuncionarioRequestDTO> funcionarios) {
        return service.inserirVarios(funcionarios);
    }


    @PutMapping("{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizar (@PathVariable Long id, @Valid @RequestBody FuncionarioRequestDTO funcionario) {
        return ResponseEntity.ok(service.alterar(id, funcionario));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
