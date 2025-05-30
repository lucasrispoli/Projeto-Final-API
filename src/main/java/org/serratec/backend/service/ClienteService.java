package org.serratec.backend.service;

import jakarta.transaction.Transactional;
import org.serratec.backend.DTO.ClienteRequestDTO;
import org.serratec.backend.DTO.ClienteResponseDTO;
import org.serratec.backend.DTO.EnderecoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Endereco;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EnderecoService service;

	public Cliente buscarClientePorID(Long id) {
		return repository.getById(id);
	}

	public ClienteResponseDTO salvarCliente(ClienteRequestDTO clienteRequestDTO) {
		Cliente cliente = new Cliente();
		EnderecoResponseDTO enderecoDTO = service.buscar(clienteRequestDTO.getCep());
		Endereco endereco = service.buscarPorId(enderecoDTO.id());
		cliente.setNome(clienteRequestDTO.getNome());
		cliente.setTelefone(clienteRequestDTO.getTelefone());
		cliente.setEmail(clienteRequestDTO.getEmail());
		cliente.setCpf(clienteRequestDTO.getCpf());
		cliente.setSenha(clienteRequestDTO.getSenha());
		cliente.setComplemento(clienteRequestDTO.getComplemento());
		cliente.setCep(clienteRequestDTO.getCep());
		cliente.setEndereco(endereco);
		cliente = repository.save(cliente);

		return new ClienteResponseDTO(cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
	}

}
