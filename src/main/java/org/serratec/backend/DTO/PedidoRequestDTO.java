package org.serratec.backend.DTO;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CurrentTimestamp;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Carrinho;
import org.serratec.backend.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequestDTO {

    @CurrentTimestamp
    private LocalDateTime dataPedido;

    @NotNull
    private StatusEnum status;
    private Cliente cliente;
    private List<Carrinho> itens;


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void setItens(List<Carrinho> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public List<Carrinho> getItens() {
        return itens;
    }
}
