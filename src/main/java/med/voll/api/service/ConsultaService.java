package med.voll.api.service;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void schedule(AgendamentoConsultaDTO agendamento) {

        if(!pacienteRepository.existsById(agendamento.pacienteId()))
            throw new ValidationExceptionApi("Id do paciente informado não existe!");

        if(agendamento.medicoId() != null && !medicoRepository.existsById(agendamento.medicoId()))
            throw new ValidationExceptionApi("Id do médico informado não existe!");

        var paciente = pacienteRepository.getReferenceById(agendamento.pacienteId());
        var medico = pickMedico(agendamento);
        var consulta = new Consulta(null, medico, paciente, agendamento.data());
        consultaRepository.save(consulta);
    }

    private Medico pickMedico(AgendamentoConsultaDTO agendamento) {

        if(agendamento.medicoId() != null)
            return medicoRepository.getReferenceById(agendamento.medicoId());

        if(agendamento.especialidade() == null)
            throw new ValidationExceptionApi("Especialidade é obrigatória quando o médico não for escolhido!");

        return medicoRepository.pickMedicoRandomFreeDate(agendamento.especialidade(), agendamento.data());
    }


}