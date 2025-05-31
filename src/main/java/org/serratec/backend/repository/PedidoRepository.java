package org.serratec.backend.repository;

import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    @Query(value = "SELECT * FROM PEDIDO WHERE ID_CLIENTE = :id" +
            "", nativeQuery = true)
    List<Pedido> listarPedidosPorIdUsuario(Long id);
}
