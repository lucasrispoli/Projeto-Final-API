package org.serratec.backend.service;

import org.serratec.backend.DTO.FuncionarioRequestDTO;
import org.serratec.backend.DTO.FuncionarioResponseDTO;
import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.config.MailConfig;
import org.serratec.backend.entity.Funcionario;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Perfil;
import org.serratec.backend.enums.StatusPessoaEnum;
import org.serratec.backend.exception.PedidoException;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MailConfig mailConfig;


    public FuncionarioResponseDTO inserir(FuncionarioRequestDTO funcionarioDTO) {
        verificaFuncPorNome(funcionarioDTO);

        Funcionario funcionarioEntity = new Funcionario();
        funcionarioEntity.setNome(funcionarioDTO.getNome());
        funcionarioEntity.setTelefone(funcionarioDTO.getTelefone());
        funcionarioEntity.setEmail(funcionarioDTO.getEmail());
        funcionarioEntity.setCpf(funcionarioDTO.getCpf());
        funcionarioEntity.setSenha(encoder.encode(funcionarioDTO.getSenha()));
        funcionarioEntity.setCargo(funcionarioDTO.getCargo());
        funcionarioEntity.setSalario(funcionarioDTO.getSalario());
        funcionarioEntity.setStatus(StatusPessoaEnum.ATIVO);
        funcionarioEntity.setPerfil(perfilService.buscar(Long.valueOf(1)));

        repository.save(funcionarioEntity);

        mailConfig.enviar(funcionarioDTO.getEmail(), "Confirmação de Cadastro do Funcionário", funcionarioDTO.getNome(),
                "Funcionário:", funcionarioDTO.toString());

        return new FuncionarioResponseDTO(funcionarioEntity.getNome(), funcionarioEntity.getTelefone(),
                funcionarioEntity.getEmail(), funcionarioEntity.getCargo(),funcionarioEntity.getSalario());

    }

    public List<FuncionarioResponseDTO> inserirVarios(List<FuncionarioRequestDTO> funcionarioDTOs) {
        List<FuncionarioResponseDTO> funcionarios = new ArrayList<>();

        for (FuncionarioRequestDTO dto : funcionarioDTOs) {
            verificaFuncPorNome(dto);

            Funcionario funcionarioEntity = new Funcionario();
            funcionarioEntity.setNome(dto.getNome());
            funcionarioEntity.setTelefone(dto.getTelefone());
            funcionarioEntity.setEmail(dto.getEmail());
            funcionarioEntity.setCpf(dto.getCpf());
            funcionarioEntity.setSenha(encoder.encode(dto.getSenha()));
            funcionarioEntity.setCargo(dto.getCargo());
            funcionarioEntity.setSalario(dto.getSalario());
            funcionarioEntity.setStatus(StatusPessoaEnum.ATIVO);
            funcionarioEntity.setPerfil(perfilService.buscar(Long.valueOf(3)));

            repository.save(funcionarioEntity);

            FuncionarioResponseDTO responseDTO = new FuncionarioResponseDTO(funcionarioEntity.getNome(),
                    funcionarioEntity.getTelefone(), funcionarioEntity.getEmail(), funcionarioEntity.getCargo(), funcionarioEntity.getSalario());

            funcionarios.add(responseDTO);

            mailConfig.enviar(dto.getEmail(), "Confirmação de Cadastro do Funcionário", dto.getNome(), "Funcionário:", dto.toString());
        }

        return funcionarios;
    }

    public List<FuncionarioResponseDTO> listar() {
        List<Funcionario> funcionarios = repository.findAll();
        List<FuncionarioResponseDTO> funcionariosDTO = new ArrayList<>();

        for (Funcionario p : funcionarios) {
            if (p.getStatus() == StatusPessoaEnum.INATIVO) {
                continue;
            }
            funcionariosDTO.add(new FuncionarioResponseDTO(p.getNome(), p.getTelefone(),
                    p.getEmail(), p.getCargo(), p.getSalario()));
        }
        return funcionariosDTO;
    }


    public FuncionarioResponseDTO listarPorId(Long id) {
        return new FuncionarioResponseDTO(verificaFuncPorId(id).get().getNome(), verificaFuncPorId(id).get().getTelefone(),
                verificaFuncPorId(id).get().getEmail(), verificaFuncPorId(id).get().getCargo(), verificaFuncPorId(id).get().getSalario());
    }


    public List<FuncionarioResponseDTO> listarFuncionariosInativos() {
        List<Funcionario> funcionarios = repository.findAll();
        List<FuncionarioResponseDTO> funcionariosDTO = new ArrayList<>();

        for (Funcionario p : funcionarios) {
            if (p.getStatus() != StatusPessoaEnum.INATIVO) {
                continue;
            }
            funcionariosDTO.add(new FuncionarioResponseDTO(p.getNome(), p.getTelefone(),
                    p.getEmail(), p.getCargo(), p.getSalario()));
        }
        return funcionariosDTO;
    }


    public FuncionarioResponseDTO alterar(Long id, FuncionarioRequestDTO funcionarioDTO) {
        verificaFuncPorId(id);

        Funcionario funcionarioEntity = new Funcionario();
        funcionarioEntity.setId(id);
        funcionarioEntity.setNome(funcionarioDTO.getNome());
        funcionarioEntity.setTelefone(funcionarioDTO.getTelefone());
        funcionarioEntity.setEmail(funcionarioDTO.getEmail());
        funcionarioEntity.setCpf(funcionarioDTO.getCpf());
        funcionarioEntity.setSenha(encoder.encode(funcionarioDTO.getSenha()));
        funcionarioEntity.setCargo(funcionarioDTO.getCargo());
        funcionarioEntity.setSalario(funcionarioDTO.getSalario());
        funcionarioEntity.setStatus(StatusPessoaEnum.ATIVO);
        funcionarioEntity.setPerfil(perfilService.buscar(Long.valueOf(3)));

        repository.save(funcionarioEntity);

        mailConfig.enviar(funcionarioDTO.getEmail(), "Alteração no cadastro do funcionário",
                funcionarioDTO.getNome(),"Funcionário:", funcionarioDTO.toString());

        return new FuncionarioResponseDTO(funcionarioEntity.getNome(), funcionarioEntity.getTelefone(),
                funcionarioEntity.getEmail(), funcionarioEntity.getCargo(),funcionarioEntity.getSalario());

    }

    public void deletar(Long id) {
        Funcionario funcionario = verificaFuncPorId(id).get();
        funcionario.setStatus(StatusPessoaEnum.INATIVO);
        repository.save(funcionario);
        mailConfig.enviar(funcionario.getEmail(), "Funcionário deletado com sucesso", funcionario.getNome(),"Funcionário:",
                funcionario.getNome() + "\nCargo: " + funcionario.getCargo() + "\nemail: " +
                        funcionario.getEmail() + "\nSalário: " + funcionario.getSalario());
    }

    //VERIFICA SE O ID DO PRODUTO INFORMADO FOI ENCONTRADO
    private Optional<Funcionario> verificaFuncPorId(Long id) {
        Optional<Funcionario> funcionario = repository.findById(id);

        if (funcionario.isEmpty()) {
//            throw new FuncionarioException("Funcionario com ID " + id + " não encontrado.");
            throw new ProdutoException("Funcionario com ID " + id + " não encontrado.");
        }
        return funcionario;
    }

    //VERIFICA SE O NOME DO PRODUTO ESTA SENDO CADASTRADO NOVAMENTE
    private void verificaFuncPorNome(FuncionarioRequestDTO p) {
        Optional<Funcionario> funcionario = repository.findByNome(p.getNome());

        if (funcionario.isPresent()) {
//            throw new FuncionarioException("Funcionario com nome '" + p.getNome() + "' já cadastrado.");
            throw new ProdutoException("Funcionario com nome '" + p.getNome() + "' já cadastrado.");
        }
    }

}


