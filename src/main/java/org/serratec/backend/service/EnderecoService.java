package org.serratec.backend.service;

import org.serratec.backend.entity.Endereco;
import org.serratec.backend.exception.EnderecoException;
import org.serratec.backend.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository repository;

     //VERIFICA SE O USUARIO DIGITOU O ENDEREÇO CORRETAMENTE
	private void validarCamposObrigatorios(Endereco endereco) {
		if (endereco.getCep() == null || endereco.getCep().isBlank() ||
				endereco.getBairro() == null || endereco.getBairro().isBlank() ||
				endereco.getUf() == null || endereco.getUf().isBlank() ||
				endereco.getLogradouro() == null || endereco.getLogradouro().isBlank()) {
			throw new EnderecoException("Campos obrigatórios do endereço estão ausentes ou inválidos.");
		}
	}

}
