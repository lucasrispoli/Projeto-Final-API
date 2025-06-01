package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.entity.AvaliacaoProduto;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.AvaliacaoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class AvaliacaoProdutoController {

	@Autowired
	private AvaliacaoProdutoRepository avaliacaoProdutoRepository;

	@ResponseBody
	@PostMapping(value = "/salvaAvaliacaoProduto")
	public ResponseEntity<AvaliacaoProduto> salvaAvaliacaoProduto(
			@Valid @RequestBody AvaliacaoProduto avaliacaoProduto) {

		if (avaliacaoProduto.getProduto() == null
				|| (avaliacaoProduto.getProduto() != null && avaliacaoProduto.getProduto().getId() <= 0)) {
			throw new ProdutoException("A avaliaçao deve conter o produto associado");

		}

		if (avaliacaoProduto.getUsuario() == null
				|| (avaliacaoProduto.getUsuario() != null && avaliacaoProduto.getUsuario().getId() <= 0)) {
			throw new ClienteException("A avaliaçao deve conter o usuário associado");

		}

		avaliacaoProduto = avaliacaoProdutoRepository.saveAndFlush(avaliacaoProduto);

		return new ResponseEntity<AvaliacaoProduto>(avaliacaoProduto, HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "/deleteAvaliacaoUsuario/{idAvaliacao}")
	public ResponseEntity<?> deleteAvalicaoPessoa(@PathVariable("idAvaliacao") Long idAvaliacao) {

		avaliacaoProdutoRepository.deleteById(idAvaliacao);

		return new ResponseEntity<String>("Avaliacao Removida", HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "/avaliacaoProduto/{idProduto}")
	public ResponseEntity<List<AvaliacaoProduto>> avaliacaoProduto(@PathVariable("idProduto") Long idProduto){
		
		List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoProduto(idProduto);

		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutos, HttpStatus.OK);
		
	}

	@ResponseBody
	@GetMapping(value = "/avaliacaoProdutoUsuario/{idProduto}/{idUsuario}")
	public ResponseEntity<List<AvaliacaoProduto>> avalicaoProdutoUsuario(@PathVariable("idProduto") Long idProduto, @PathVariable("idUsuario") Long idUsuario){
		
		List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoProdutoUsuario(idProduto, idUsuario);

		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutos, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "/avaliacaoUsuario/{idUsuario}")
	public ResponseEntity<List<AvaliacaoProduto>> avaliacaoUsuario(@PathVariable("idUsuario") Long idUsuario){
		
		List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoProduto(idUsuario);

		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutos, HttpStatus.OK);
		
	}
	
	
	
	
}
