package med.voll.api.validation.scheduling;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorMedicoConsultationTime implements ValidatorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validate(AgendamentoConsultaDTO agendamento) {
        var hasMedicoConsultation = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(agendamento.medicoId(), agendamento.data());

        if(hasMedicoConsultation)
            throw new ValidationExceptionApi("Médico já possui outra consulta agendada nesse mesmo horário!");
    }
}
