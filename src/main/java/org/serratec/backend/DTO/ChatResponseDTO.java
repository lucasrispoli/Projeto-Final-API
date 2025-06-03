package org.serratec.backend.DTO;

public class ChatResponseDTO {

        private String resposta;

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public ChatResponseDTO(String resposta) {
            this.resposta = resposta;
        }
    }

