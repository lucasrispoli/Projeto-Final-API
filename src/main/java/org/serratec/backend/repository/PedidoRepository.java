package org.serratec.backend.repository;

import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    @Query(value = "SELECT * FROM PEDIDO WHERE ID_CLIENTE = :id", nativeQuery = true)
    List<Pedido> listarPedidosPorIdUsuario(Long id);

    @Query(value = "SELECT pd.id , pd.nome, pd.valor, pd.id_categoria, pd.plataforma, c.nome as cat_nome\n" +
            "FROM PEDIDO p\n" +
            "JOIN carrinho cr on cr.id_pedido = p.id\n" +
            "JOIN produto pd on cr.id_produto = pd.id\n" +
            "JOIN categoria c on c.id = pd.id_categoria\n" +
            "WHERE p.id = :id;", nativeQuery = true)
    List<Produto> obterProdutosPorPedido(Long id);
}
