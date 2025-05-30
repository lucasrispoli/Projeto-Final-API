package org.serratec.backend.DTO;

import org.serratec.backend.entity.Endereco;

public record EnderecoResponseDTO(Long id, String cep, String logradouro, String bairro, String localidade, String uf) {

    public EnderecoResponseDTO(Endereco endereco) {
        this(endereco.getId(),endereco.getCep(), endereco.getLogradouro(), endereco.getBairro(), endereco.getLocalidade(), endereco.getUf());

    }
}
