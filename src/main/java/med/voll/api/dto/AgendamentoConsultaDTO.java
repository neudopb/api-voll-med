package med.voll.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.enuns.EspecialidadeEnum;

import java.time.LocalDateTime;

public record AgendamentoConsultaDTO(
        @JsonAlias("medico_id")
        Long medicoId,
        @NotNull
        @JsonAlias("paciente_id")
        Long pacienteId,
        @NotNull
        @Future
        LocalDateTime data,
        EspecialidadeEnum especialidade) {
}