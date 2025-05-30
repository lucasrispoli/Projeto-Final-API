package org.serratec.backend.controller;

import org.serratec.backend.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")

public class EnderecoController {
	
	@Autowired
	private EnderecoService service;

	
	
}
