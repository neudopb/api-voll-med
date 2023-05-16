package med.voll.api.validation;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidatorClinicWorkingHours implements ValidatorAgendamentoConsulta {

    public void validate(AgendamentoConsultaDTO agendamento) {
        var dateConsulta = agendamento.data();

        var isSunday = dateConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var isBeforeOpening = dateConsulta.getHour() < 7;
        var isAfterClosing = dateConsulta.getHour() > 18;

        if(isSunday || isBeforeOpening || isAfterClosing)
            throw new ValidationExceptionApi("Consulta fora do horário de funcionamento da clínica!");
    }
}
