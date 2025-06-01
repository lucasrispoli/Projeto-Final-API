package org.serratec.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import org.serratec.backend.entity.Categoria;

public class CategoriaRequestDTO {
	
	@NotBlank
    private String nome;

    public CategoriaRequestDTO() {
    }

    public CategoriaRequestDTO(Categoria categoria) {
        this.nome = categoria.getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
