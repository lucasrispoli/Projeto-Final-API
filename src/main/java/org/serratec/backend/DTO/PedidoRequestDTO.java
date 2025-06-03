package org.serratec.backend.DTO;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CurrentTimestamp;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.PK.Carrinho;
import org.serratec.backend.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequestDTO {


    @NotNull
    private Cliente cliente;
    private List<Carrinho> itens;


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }



    public void setItens(List<Carrinho> itens) {
        this.itens = itens;
    }



    public List<Carrinho> getItens() {
        return itens;
    }
}
