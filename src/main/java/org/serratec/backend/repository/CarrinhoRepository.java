package org.serratec.backend.repository;

import org.serratec.backend.entity.PK.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    List<Carrinho> id(Long id);

    @Query(value = "SELECT * FROM CARRINHO WHERE ID_PEDIDO = :pedido", nativeQuery = true)
    List<Carrinho> carregarPedidos(Long pedido);
//
//    @Query(value = "select * from funcionario where nome ilike CONCAT('%', :nome,'%' )", nativeQuery = true)
//    Page<Funcionario> listarPorNome(String nome, Pageable pageable);
}
