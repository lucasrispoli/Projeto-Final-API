package org.serratec.backend.service;

import jakarta.transaction.Transactional;
import org.serratec.backend.DTO.ClienteRequestDTO;
import org.serratec.backend.DTO.ClienteResponseDTO;
import org.serratec.backend.DTO.EnderecoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Endereco;
import org.serratec.backend.enums.StatusPessoaEnum;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EnderecoService service;

	public Cliente buscarClientePorID(Long id) {
		return repository.getById(id);
	}

	public ResponseEntity<ClienteResponseDTO> listarPorId(Long id){
		Optional<Cliente> cliente = repository.findById(id);
		if(cliente.isPresent()){

			return ResponseEntity.ok(new ClienteResponseDTO(cliente.get().getNome(), cliente.get().getTelefone(), cliente.get().getEmail()));
		}
		throw new ClienteException("Cliente não encontrado!");
	}
	public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
		List<Cliente> clientes = repository.findAll();
		List<ClienteResponseDTO> clienteResponseDTOs =
								clientes.stream()
								.map(cliente -> new ClienteResponseDTO(cliente.getNome(), cliente.getTelefone(), cliente.getEmail()))
								.collect(Collectors.toList());
		return ResponseEntity.ok(clienteResponseDTOs);
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
		cliente.setStatus(clienteRequestDTO.getStatus());
		cliente = repository.save(cliente);

		return new ClienteResponseDTO(cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
	}

	public ClienteResponseDTO atualizarCliente(Long id,ClienteRequestDTO clienteRequestDTO) {
		var cliente = repository.findById(id);
		if(cliente.isPresent()) {
			EnderecoResponseDTO enderecoDTO = service.buscar(clienteRequestDTO.getCep());
			Endereco endereco = service.buscarPorId(enderecoDTO.id());
			cliente.get().setNome((clienteRequestDTO.getNome()));
			cliente.get().setTelefone(clienteRequestDTO.getTelefone());
			cliente.get().setEmail(clienteRequestDTO.getEmail());
			cliente.get().setCpf(clienteRequestDTO.getCpf());
			cliente.get().setSenha(clienteRequestDTO.getSenha());
			cliente.get().setComplemento(clienteRequestDTO.getComplemento());
			cliente.get().setCep(clienteRequestDTO.getCep());
			cliente.get().setEndereco(endereco);
			cliente.get().setStatus(clienteRequestDTO.getStatus());
			repository.save(cliente.get());
			return new ClienteResponseDTO(cliente.get().getNome(), cliente.get().getTelefone(), cliente.get().getEmail());
		}
		throw new ClienteException("Cliente não encontrado!");
	}
	public ResponseEntity deletar(Long id) {
		Optional<Cliente> cliente = repository.findById(id);
		System.out.println(cliente.get().getId());
		if(cliente.isPresent()) {
			cliente.get().setStatus(StatusPessoaEnum.DELETADO);
			repository.save(cliente.get());
			return ResponseEntity.noContent().build();
		}
		throw new ClienteException("Cliente não encontrado!");
	}
}
