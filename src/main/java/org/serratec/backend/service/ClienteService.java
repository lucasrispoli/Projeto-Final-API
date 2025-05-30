package org.serratec.backend.service;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;

	public Cliente buscarClientePorID(Long id) {
		return repository.getById(id);
	}

}
