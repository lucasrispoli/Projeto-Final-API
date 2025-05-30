package org.serratec.backend.service;

import org.apache.catalina.connector.ClientAbortException;
import org.aspectj.apache.bcel.classfile.Module;
import org.serratec.backend.DTO.ProdutoRequestDTO;
import org.serratec.backend.DTO.ProdutoResponseDTO;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        return new  ProdutoResponseDTO(produtoEntity.getNome(), produtoEntity.getValor(), produtoEntity.getCategoria());

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


    //Deverá exibir a categoria de cada produto
    public List<ProdutoResponseDTO> listar() {
        List<Produto> produtos =  repository.findAll();
        List<ProdutoResponseDTO> produtosDTO =  new ArrayList<>();

        for (Produto p : produtos) {
            produtosDTO.add(new ProdutoResponseDTO(p.getNome(),p.getValor(), p.getCategoria()));
        }
        return produtosDTO;
    }

//    public void deletar(Long id) {
//        verificaProdPorId(id);
//        repository.deleteById(id);
//    }


    private void verificaProdPorNome(ProdutoRequestDTO p) {
        Optional<Produto> produto = repository.findByNome(p.getNome());

        if (produto.isPresent()) {
            throw new ClienteException("MUDAR O TRATAMENTO DE ERRO, SÓ COLOQUEI PARA NÃO DAR ERRO");
        }
    }
//    private void verificaProdPorId(Long id) {
//        Optional<Produto> produto = repository.findById(id);
//
//        if (produto.isEmpty()) {
//            throw new ClienteException("MUDAR O TRATAMENTO DE ERRO, SÓ COLOQUEI PARA NÃO DAR ERRO");
//        }
//    }


}
