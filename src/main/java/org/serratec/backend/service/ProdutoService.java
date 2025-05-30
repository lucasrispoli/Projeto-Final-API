package org.serratec.backend.service;

import org.serratec.backend.DTO.ProdutoRequestDTO;
import org.serratec.backend.DTO.ProdutoResponseDTO;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;


    public ProdutoResponseDTO inserir(ProdutoRequestDTO produtoDTO) {
        verificaProdPorNome(produtoDTO);

        Produto produtoEntity = new Produto();
        produtoEntity.setNome(produtoDTO.getNome());
        produtoEntity.setValor(produtoDTO.getValor());
        produtoEntity.setCategoria(produtoDTO.getCategoria());

        repository.save(produtoEntity);

        return new ProdutoResponseDTO(produtoEntity.getNome(), produtoEntity.getValor(), produtoEntity.getCategoria());

    }
    public List<ProdutoResponseDTO> inserirVarios(List<ProdutoRequestDTO> produtoDTOs) {
        List<ProdutoResponseDTO> produtos = new ArrayList<>();

        for (ProdutoRequestDTO dto : produtoDTOs) {
            verificaProdPorNome(dto);

            Produto produtoEntity = new Produto();
            produtoEntity.setNome(dto.getNome());
            produtoEntity.setValor(dto.getValor());
            produtoEntity.setCategoria(dto.getCategoria());

            repository.save(produtoEntity);

            ProdutoResponseDTO responseDTO = new ProdutoResponseDTO(produtoEntity.getNome(), produtoEntity.getValor(),
                    produtoEntity.getCategoria());

            produtos.add(responseDTO);
        }

        return produtos;
    }
    public List<ProdutoResponseDTO> listar() {
        List<Produto> produtos = repository.findAll();
        List<ProdutoResponseDTO> produtosDTO = new ArrayList<>();

        for (Produto p : produtos) {
            produtosDTO.add(new ProdutoResponseDTO(p.getNome(), p.getValor(), p.getCategoria()));
        }
        return produtosDTO;
    }


    public ProdutoResponseDTO alterar(Long id, ProdutoRequestDTO produtoDTO) {
        verificaProdPorId(id);

        Produto produtoEntity = new Produto();
        produtoEntity.setId(id);
        produtoEntity.setNome(produtoDTO.getNome());
        produtoEntity.setValor(produtoDTO.getValor());
        produtoEntity.setCategoria(produtoDTO.getCategoria());
        repository.save(produtoEntity);

        ProdutoResponseDTO prodRespDTO = new ProdutoResponseDTO(produtoEntity.getNome(), produtoEntity.getValor(),
                produtoEntity.getCategoria());

        return prodRespDTO;
    }

    public void deletar(Long id) {
        verificaProdPorId(id);
        repository.deleteById(id);
    }
    //VERIFICA SE O ID DO PRODUTO INFORMADO FOI ENCONTRADO
    private void verificaProdPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new ProdutoException("Produto com ID " + id + " não encontrado.");
        }
    }
 //VERIFICA SE O NOME DO PRODUTO ESTA SENDO CADASTRADO NOVAMENTE
    private void verificaProdPorNome(ProdutoRequestDTO dto) {
        if (repository.existsByNome(dto.getNome())) {
            throw new ProdutoException("Produto com nome '" + dto.getNome() + "' já cadastrado.");
        }
    }
    }


