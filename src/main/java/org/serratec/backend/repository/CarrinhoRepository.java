package org.serratec.backend.repository;

import org.serratec.backend.DTO.CarrinhoResponseDTO;
import org.serratec.backend.entity.PK.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    List<Carrinho> id(Long id);

    @Query(value = "SELECT * FROM CARRINHO WHERE ID_PEDIDO = :pedido", nativeQuery = true)
    List<Carrinho> carregarPedidos(Long pedido);

    @Query(value = "SELECT * FROM CARRINHO WHERE ID_PEDIDO = :idPedido", nativeQuery = true)
    List<Carrinho> buscarItensPorIdPedido(Long idPedido);
}
