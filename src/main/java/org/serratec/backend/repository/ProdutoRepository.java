package org.serratec.backend.repository;

import org.serratec.backend.entity.Produto;
import org.serratec.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNome(String nome);

    //VERIFICA O NOME
    boolean existsByNome(String nome);
//VERIFICA O ID
    Optional<Produto> findById(Long id);
}
