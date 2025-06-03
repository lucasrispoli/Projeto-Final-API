package org.serratec.backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.serratec.backend.DTO.ClienteRequestDTO;
import org.serratec.backend.DTO.ClienteResponseDTO;
import org.serratec.backend.DTO.EnderecoResponseDTO;
import org.serratec.backend.config.MailConfig;
import org.serratec.backend.database.PostgreSQLAuditor;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Endereco;
import org.serratec.backend.entity.Usuario;
import org.serratec.backend.enums.StatusPessoaEnum;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MailConfig mailConfig;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostgreSQLAuditor auditor;


    public Cliente buscarClientePorID(Long id) {
        return repository.getById(id);
    }

    public ResponseEntity<ClienteResponseDTO> listarPorId(Long id) {
        Optional<Cliente> cliente = repository.findById(id);
        if (cliente.isPresent()) {

            return ResponseEntity.ok(new ClienteResponseDTO(cliente.get().getNome(), cliente.get().getTelefone(), cliente.get().getEmail()));
        }
        throw new ClienteException("Cliente não encontrado!");
    }

    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<Cliente> clientes = repository.findAll();
        List<ClienteResponseDTO> clienteResponseDTOs =
                clientes.stream()
                        .filter(cliente -> cliente.getStatus() != StatusPessoaEnum.DELETADO)
                        .map(cliente -> new ClienteResponseDTO(cliente.getNome(), cliente.getTelefone(), cliente.getEmail()))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(clienteResponseDTOs);
    }

    public ResponseEntity<List<ClienteResponseDTO>> listarClientesDeletados() {
        List<Cliente> clientes = repository.findAll();
        List<ClienteResponseDTO> clienteResponseDTOs =
                clientes.stream()
                        .filter(cliente -> cliente.getStatus() == StatusPessoaEnum.DELETADO)
                        .map(cliente -> new ClienteResponseDTO(cliente.getNome(), cliente.getTelefone(), cliente.getEmail()))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(clienteResponseDTOs);
    }

    @Transactional
    public ClienteResponseDTO salvarCliente(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = new Cliente();

        EnderecoResponseDTO enderecoDTO = service.buscar(clienteRequestDTO.getCep());
        Endereco endereco = service.buscarPorId(enderecoDTO.id());
        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setTelefone(clienteRequestDTO.getTelefone());
        cliente.setEmail(clienteRequestDTO.getEmail());
        cliente.setCpf(clienteRequestDTO.getCpf());
        cliente.setSenha(encoder.encode(clienteRequestDTO.getSenha()));
        cliente.setComplemento(clienteRequestDTO.getComplemento());
        cliente.setEndereco(endereco);
        cliente.setStatus(StatusPessoaEnum.ATIVO);
        cliente.setPerfil(perfilService.buscar(Long.valueOf(2)));

        auditor.definirUsuario(Usuario.getUsuarioLogado());
        cliente = repository.save(cliente);

        mailConfig.enviar(clienteRequestDTO.getEmail(), "Confirmação de Cadastro", cliente.getNome(),
                "Cliente:", clienteRequestDTO.toString(), "CadastroTemplate");

        return new ClienteResponseDTO(cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
    }

    @Transactional
    public ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO clienteRequestDTO) {
        var cliente = repository.findById(id);

        if (cliente.isPresent()) {
            EnderecoResponseDTO enderecoDTO = service.buscar(clienteRequestDTO.getCep());
            Endereco endereco = service.buscarPorId(enderecoDTO.id());
            cliente.get().setNome((clienteRequestDTO.getNome()));
            cliente.get().setTelefone(clienteRequestDTO.getTelefone());
            cliente.get().setEmail(clienteRequestDTO.getEmail());
            cliente.get().setCpf(clienteRequestDTO.getCpf());
            cliente.get().setSenha(encoder.encode(clienteRequestDTO.getSenha()));
            cliente.get().setComplemento(clienteRequestDTO.getComplemento());
            cliente.get().setEndereco(endereco);
            cliente.get().setStatus(StatusPessoaEnum.ATIVO);
            cliente.get().setPerfil(perfilService.buscar(Long.valueOf(2)));
            auditor.definirUsuario(Usuario.getUsuarioLogado());
            repository.save(cliente.get());

            mailConfig.enviar(clienteRequestDTO.getEmail(), "Alteração no cadastro do cliente",
                    clienteRequestDTO.getNome(),"Cliente:", clienteRequestDTO.toString(), "CadastroTemplate");

            return new ClienteResponseDTO(cliente.get().getNome(), cliente.get().getTelefone(), cliente.get().getEmail());
        }
        throw new ClienteException("Cliente não encontrado!");
    }

    @Transactional
    public ResponseEntity deletar(Long id) {
        Optional<Cliente> cliente = repository.findById(id);
        if (cliente.isPresent()) {
            cliente.get().setStatus(StatusPessoaEnum.DELETADO);
            auditor.definirUsuario(Usuario.getUsuarioLogado());
            repository.save(cliente.get());

            mailConfig.enviar(cliente.get().getEmail(), "Cliente deletado com sucesso", cliente.get().getNome(),"Cliente:",
                    cliente.get().getNome() + "\nemail: " + cliente.get().getEmail(), "CadastroTemplate");
            return ResponseEntity.noContent().build();
        }
        throw new ClienteException("Cliente não encontrado!");
    }
}
