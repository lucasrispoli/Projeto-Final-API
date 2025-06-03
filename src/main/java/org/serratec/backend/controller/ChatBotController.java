package org.serratec.backend.controller;

import org.serratec.backend.DTO.ChatRequestDTO;
import org.serratec.backend.DTO.ChatResponseDTO;
import org.serratec.backend.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
public class ChatBotController {

    @Autowired
    protected ChatBotService service;


    @PostMapping
    public ResponseEntity<ChatResponseDTO> conversar(@RequestBody ChatRequestDTO request ) {
        String pergunta = request.getMensagem().toLowerCase();

        String resposta = service.gerarResposta(pergunta);

        return ResponseEntity.ok(new ChatResponseDTO(resposta));
    }
}

