package org.serratec.backend.service;


import org.serratec.backend.entity.Cliente;
import org.serratec.backend.exception.ClienteException;

import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
    @Autowired
    private ClienteRepository clienteRepository;

	public Cliente buscarClientePorID(Long id) {
		return repository.getById(id);
	}
//caso o cpf ja esteja cadastrado e tente cadastra-lo
	public Cliente salvar(Cliente cliente) {
		if (repository.existsByCpf(cliente.getCpf())) {
			throw new ClienteException("Já existe um cliente cadastrado com o cpf: " + cliente.getCpf());
		}
		return repository.save(cliente);

	}
	//CASO O CPF CADASTRADO NAO SEJA ENCONTRADO
	public Cliente buscarPorCpf(String cpf) {
		return repository.findByCpf(cpf)
				.orElseThrow(() -> new ClienteException("Cliente com CPF " + cpf + " não encontrado."));

	}

}
