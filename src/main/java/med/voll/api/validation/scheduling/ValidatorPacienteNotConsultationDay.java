package med.voll.api.validation.scheduling;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorPacienteNotConsultationDay implements ValidatorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validate(AgendamentoConsultaDTO agendamento) {
        var firstHours = agendamento.data().withHour(7);
        var lastHours = agendamento.data().withHour(18);
        var hasPacienteHaveConsultationDay = repository.existsByPacienteIdAndDataBetween(agendamento.pacienteId(), firstHours, lastHours);

        if(hasPacienteHaveConsultationDay)
            throw new ValidationExceptionApi("Paciente já possui uma consulta agendada nesse dia!");
    }
}
