package org.serratec.backend.service;

import org.springframework.stereotype.Service;

@Service
public class ChatBotService {

   public String gerarResposta(String pergunta) {
        if (pergunta.contains("forma de pagamento") || pergunta.contains("pagamento")) {
            return "Aceitamos cartão de crédito, débito e Pix.";
        } else if (pergunta.contains("frete") || pergunta.contains("entrega")) {
            return "O frete é grátis para compras acima de R$200.";
        } else if (pergunta.contains("horário") || pergunta.contains("funciona")) {
            return "Atendemos de segunda a sexta, das 9h às 18h.";
        } else {
            return "Desculpe, não entendi sua pergunta. Pode reformular?";
        }
    }
}
