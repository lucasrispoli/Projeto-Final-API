package org.serratec.backend.repository;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Funcionario;
import org.serratec.backend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByNome(String nome);
    Optional<Funcionario> findByCpf(String cpf);
    Optional<Funcionario> findByEmail(String email);
}
