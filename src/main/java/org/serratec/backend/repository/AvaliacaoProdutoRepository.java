package org.serratec.backend.repository;

import java.util.List;

import org.serratec.backend.entity.AvaliacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {

	@Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1")
	List<AvaliacaoProduto> avaliacaoProduto(Long idProduto);
	
	@Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1 and a.usuario.id = ?2")
	List<AvaliacaoProduto> avaliacaoProdutoUsuario(Long idProduto, Long idUsuario);
	
	@Query(value = "select a from AvaliacaoProduto a where a.usuario.id = ?1")
	List<AvaliacaoProduto> avaliacaoUsuario(Long idUsuario);
	
	
}
