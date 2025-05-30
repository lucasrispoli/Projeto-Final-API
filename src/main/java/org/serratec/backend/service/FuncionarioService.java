package org.serratec.backend.service;

import org.serratec.backend.DTO.FuncionarioRequestDTO;
import org.serratec.backend.DTO.FuncionarioResponseDTO;
import org.serratec.backend.entity.Funcionario;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;


    public FuncionarioResponseDTO inserir(FuncionarioRequestDTO funcionarioDTO) {
        verificaFuncPorNome(funcionarioDTO);

        Funcionario funcionarioEntity = new Funcionario();
        funcionarioEntity.setNome(funcionarioDTO.getNome());
        funcionarioEntity.setTelefone(funcionarioDTO.getTelefone());
        funcionarioEntity.setEmail(funcionarioDTO.getEmail());
        funcionarioEntity.setCpf(funcionarioDTO.getCpf());
        funcionarioEntity.setSenha(funcionarioDTO.getSenha());
        funcionarioEntity.setCargo(funcionarioDTO.getCargo());
        funcionarioEntity.setSalario(funcionarioDTO.getSalario());

        repository.save(funcionarioEntity);

        return new FuncionarioResponseDTO(funcionarioEntity.getNome(), funcionarioEntity.getTelefone(),
                funcionarioEntity.getEmail(), funcionarioEntity.getSalario());

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
            funcionarioEntity.setSenha(dto.getSenha());
            funcionarioEntity.setCargo(dto.getCargo());
            funcionarioEntity.setSalario(dto.getSalario());

            repository.save(funcionarioEntity);

            FuncionarioResponseDTO responseDTO = new FuncionarioResponseDTO(funcionarioEntity.getNome(), funcionarioEntity.getTelefone(),
                    funcionarioEntity.getEmail(), funcionarioEntity.getSalario());

            funcionarios.add(responseDTO);
        }

        return funcionarios;
    }

    public List<FuncionarioResponseDTO> listar() {
        List<Funcionario> funcionarios = repository.findAll();
        List<FuncionarioResponseDTO> funcionariosDTO = new ArrayList<>();

        for (Funcionario p : funcionarios) {
            funcionariosDTO.add(new FuncionarioResponseDTO(p.getNome(), p.getTelefone(),
                    p.getEmail(), p.getSalario()));
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
        funcionarioEntity.setSenha(funcionarioDTO.getSenha());
        funcionarioEntity.setCargo(funcionarioDTO.getCargo());
        funcionarioEntity.setSalario(funcionarioDTO.getSalario());

        repository.save(funcionarioEntity);

        return new FuncionarioResponseDTO(funcionarioEntity.getNome(), funcionarioEntity.getTelefone(),
                funcionarioEntity.getEmail(), funcionarioEntity.getSalario());

    }

    public void deletar(Long id) {
        verificaFuncPorId(id);
        repository.deleteById(id);
    }

    //VERIFICA SE O ID DO PRODUTO INFORMADO FOI ENCONTRADO
    private void verificaFuncPorId(Long id) {
        Optional<Funcionario> funcionario = repository.findById(id);

        if (funcionario.isEmpty()) {
//            throw new FuncionarioException("Funcionario com ID " + id + " não encontrado.");
            throw new ProdutoException("Funcionario com ID " + id + " não encontrado.");
        }
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


