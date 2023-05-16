package med.voll.api.validation.scheduling;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidatorAntecedentTimeAgendamento")
public class ValidatorAntecedentTime implements ValidatorAgendamentoConsulta {

    public void validate(AgendamentoConsultaDTO agendamento) {
        var dateConsulta = agendamento.data();
        var now = LocalDateTime.now();
        var differenceMinutes = Duration.between(now, dateConsulta).toMinutes();

        if(differenceMinutes < 30)
            throw new ValidationExceptionApi("Consulta deve ser agendada com antecedência mínima de 30 minutos!");
    }
}
