package med.voll.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import med.voll.api.model.Consulta;

import java.time.LocalDateTime;

public record ConsultaDetailDTO(
        Long id,
        Long medicoId,
        Long pacienteId,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data) {
    public ConsultaDetailDTO(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}

