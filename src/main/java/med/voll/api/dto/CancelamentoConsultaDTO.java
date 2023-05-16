package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.enuns.MotivoCancelamentoEnum;

public record CancelamentoConsultaDTO(
        @NotNull
        Long consultaId,
        @NotNull
        MotivoCancelamentoEnum motivo) {
}
