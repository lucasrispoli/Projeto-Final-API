package org.serratec.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import org.serratec.backend.enums.StatusEnum;

public class StatusDTO {

    private StatusEnum status;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
