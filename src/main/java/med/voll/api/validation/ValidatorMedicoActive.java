package med.voll.api.validation;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorMedicoActive implements ValidatorAgendamentoConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validate(AgendamentoConsultaDTO agendamento) {
        if(agendamento.medicoId() == null)
            return;

        var isActive = repository.findAtivoById(agendamento.medicoId());

        if(!isActive)
            throw new ValidationExceptionApi("Consulta não pode ser agendada com médico excluído!");
    }
}
