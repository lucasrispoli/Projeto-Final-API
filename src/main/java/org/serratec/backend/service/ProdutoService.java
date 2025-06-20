package org.serratec.backend.service;

import org.serratec.backend.DTO.ProdutoRequestDTO;
import org.serratec.backend.DTO.ProdutoResponseDTO;
import org.serratec.backend.database.PostgreSQLAuditor;
import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.entity.Usuario;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private PostgreSQLAuditor auditor;

    @Autowired
    private CategoriaService categoriaService;


    @Transactional
    public ProdutoResponseDTO inserir(ProdutoRequestDTO produtoDTO) {
        verificaProdPorNome(produtoDTO);
        Categoria categoria = categoriaService.buscarCategoriaPorId(produtoDTO.getCategoria().getId());
        Produto produtoEntity = new Produto();
        produtoEntity.setNome(produtoDTO.getNome());
        produtoEntity.setValor(produtoDTO.getValor());
        produtoEntity.setCategoria(categoria);
        produtoEntity.setPlataforma(produtoDTO.getPlataforma());

        auditor.definirUsuario(Usuario.getUsuarioLogado());
        repository.save(produtoEntity);

        return new ProdutoResponseDTO(produtoEntity.getNome(), produtoEntity.getValor(), produtoEntity.getCategoria().getNome(), produtoEntity.getPlataforma());

    }

    @Transactional
    public List<ProdutoResponseDTO> inserirVarios(List<ProdutoRequestDTO> produtoDTOs) {
        List<ProdutoResponseDTO> produtos = new ArrayList<>();

        for (ProdutoRequestDTO dto : produtoDTOs) {
            verificaProdPorNome(dto);

            Produto produtoEntity = new Produto();
            produtoEntity.setNome(dto.getNome());
            produtoEntity.setValor(dto.getValor());
            produtoEntity.setCategoria(dto.getCategoria());

            auditor.definirUsuario(Usuario.getUsuarioLogado());
            repository.save(produtoEntity);

            ProdutoResponseDTO responseDTO = new ProdutoResponseDTO(produtoEntity.getNome(), produtoEntity.getValor(),
                    produtoEntity.getCategoria().getNome(), produtoEntity.getPlataforma());

            produtos.add(responseDTO);
        }

        return produtos;
    }

    public List<ProdutoResponseDTO> listar() {
        List<Produto> produtos = repository.findAll();
        List<ProdutoResponseDTO> produtosDTO = new ArrayList<>();

        for (Produto p : produtos) {
            produtosDTO.add(new ProdutoResponseDTO(p.getNome(), p.getValor(), p.getCategoria().getNome(), p.getPlataforma()));
        }
        return produtosDTO;
    }

    @Transactional
    public ProdutoResponseDTO alterar(Long id, ProdutoRequestDTO produtoDTO) {
        verificaProdPorId(id);

        Produto produtoEntity = new Produto();
        produtoEntity.setId(id);
        produtoEntity.setNome(produtoDTO.getNome());
        produtoEntity.setValor(produtoDTO.getValor());
        produtoEntity.setCategoria(produtoDTO.getCategoria());
        auditor.definirUsuario(Usuario.getUsuarioLogado());
        repository.save(produtoEntity);

        return new ProdutoResponseDTO(produtoEntity.getNome(), produtoEntity.getValor(),
                produtoEntity.getCategoria().getNome(), produtoEntity.getPlataforma());

    }

    @Transactional
    public void deletar(Long id) {
        verificaProdPorId(id);
        auditor.definirUsuario(Usuario.getUsuarioLogado());
        repository.deleteById(id);
    }

    private void verificaProdPorId(Long id) {
        Optional<Produto> produto = repository.findById(id);

        if (produto.isEmpty()) {
            throw new ProdutoException("Produto com ID " + id + " não encontrado.");
        }
    }

    private void verificaProdPorNome(ProdutoRequestDTO p) {
        Optional<Produto> produto = repository.findByNome(p.getNome());

        if (produto.isPresent()) {
            throw new ProdutoException("Produto com nome '" + p.getNome() + "' já cadastrado.");
        }
    }
}


