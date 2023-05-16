package med.voll.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ConsultaDetailDTO(
        Long id,
        Long medicoId,
        Long pacienteId,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data) {
}

