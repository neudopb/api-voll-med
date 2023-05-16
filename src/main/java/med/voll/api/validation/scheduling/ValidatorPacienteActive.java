package med.voll.api.validation.scheduling;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorPacienteActive implements ValidatorAgendamentoConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validate(AgendamentoConsultaDTO agendamento) {
        var isActive = repository.findAtivoById(agendamento.pacienteId());

        if(!isActive)
            throw new ValidationExceptionApi("Consulta não pode ser agendada com paciente excluído!");
    }
}
