package org.serratec.backend.service;

import org.serratec.backend.DTO.CategoriaRequestDTO;
import org.serratec.backend.DTO.CategoriaResponseDTO;
import org.serratec.backend.entity.Categoria;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;


    public CategoriaResponseDTO inserir(CategoriaRequestDTO categoriaDTO) {
        verificaCatgPorNome(categoriaDTO);

        Categoria categoriaEntity = new Categoria();
        categoriaEntity.setNome(categoriaDTO.getNome());

        repository.save(categoriaEntity);

        return new  CategoriaResponseDTO(categoriaEntity.getNome());

    }


    public List<CategoriaResponseDTO> inserirVarios(List<CategoriaRequestDTO> categoriaDTOs) {
        List<CategoriaResponseDTO> categorias = new ArrayList<>();

        for (CategoriaRequestDTO dto : categoriaDTOs) {
            verificaCatgPorNome(dto);

            Categoria categoriaEntity = new Categoria();
            categoriaEntity.setNome(dto.getNome());

            repository.save(categoriaEntity);

            CategoriaResponseDTO CatRespDTO = new CategoriaResponseDTO(categoriaEntity.getNome());

            categorias.add(CatRespDTO);
        }

        return categorias;
    }


    public List<CategoriaResponseDTO> listar() {
        List<Categoria> categorias =  repository.findAll();
        List<CategoriaResponseDTO> categoriasDTO =  new ArrayList<>();

        for (Categoria p : categorias) {
            categoriasDTO.add(new CategoriaResponseDTO(p.getNome()));
        }
        return categoriasDTO;
    }


    public CategoriaResponseDTO alterar(Long id, CategoriaRequestDTO categoriaDTO) {
        verificaCatgPorId(id);

        Categoria categoriaEntity = new Categoria();
        categoriaEntity.setId(id);
        categoriaEntity.setNome(categoriaDTO.getNome());
        repository.save(categoriaEntity);

        return new CategoriaResponseDTO(categoriaEntity.getNome());
    }


    public void deletar(Long id) {
        verificaCatgPorId(id);
        repository.deleteById(id);
    }


    private void verificaCatgPorNome(CategoriaRequestDTO p) {
        Optional<Categoria> categoria = repository.findByNome(p.getNome());

        if (categoria.isPresent()) {
//            Categoria JÁ EXISTE
            throw new ClienteException("MUDAR O TRATAMENTO DE ERRO, SÓ COLOQUEI PARA NÃO DAR ERRO");
        }
    }


    private void verificaCatgPorId(Long id) {
        Optional<Categoria> categoria = repository.findById(id);

        if (categoria.isEmpty()) {
//            Categoria NÃO EXISTE
            throw new ClienteException("MUDAR O TRATAMENTO DE ERRO, SÓ COLOQUEI PARA NÃO DAR ERRO");
        }
    }


}
