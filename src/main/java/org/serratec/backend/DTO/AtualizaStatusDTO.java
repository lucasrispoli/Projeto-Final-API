package org.serratec.backend.DTO;

import org.serratec.backend.enums.StatusEnum;

public class AtualizaStatusDTO {
    private StatusEnum status;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
