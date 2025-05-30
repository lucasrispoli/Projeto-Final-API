package org.serratec.backend.DTO;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {
	
	@NotBlank
    private String nome;

    public CategoriaRequestDTO() {
    }

    public CategoriaRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
