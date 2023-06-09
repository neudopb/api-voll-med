package med.voll.api.service;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.dto.CancelamentoConsultaDTO;
import med.voll.api.dto.ConsultaDetailDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.validation.cancellation.ValidatorCancelamentoConsulta;
import med.voll.api.validation.scheduling.ValidatorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidatorAgendamentoConsulta> validations;

    @Autowired
    private List<ValidatorCancelamentoConsulta> validationsCancelamento;

    public ConsultaDetailDTO schedule(AgendamentoConsultaDTO agendamento) {

        if(!pacienteRepository.existsById(agendamento.pacienteId()))
            throw new ValidationExceptionApi("Id do paciente informado não existe!");

        if(agendamento.medicoId() != null && !medicoRepository.existsById(agendamento.medicoId()))
            throw new ValidationExceptionApi("Id do médico informado não existe!");

        validations.forEach(v -> v.validate(agendamento));

        var paciente = pacienteRepository.getReferenceById(agendamento.pacienteId());
        var medico = pickMedico(agendamento);

        if(medico == null) {
            throw new ValidationExceptionApi("Não existe médico disponível nessa data!");
        }

        var consulta = new Consulta(null, medico, paciente, agendamento.data(), null);
        consultaRepository.save(consulta);

        return new ConsultaDetailDTO(consulta);
    }

    public void cancel(CancelamentoConsultaDTO cancelamento) {

        if(!consultaRepository.existsById(cancelamento.consultaId()))
            throw new ValidationExceptionApi("Id da consulta informada não existe!");

        validationsCancelamento.forEach(v -> v.validate(cancelamento));

        var consulta = consultaRepository.getReferenceById(cancelamento.consultaId());
        consulta.cancel(cancelamento.motivo());
    }

    private Medico pickMedico(AgendamentoConsultaDTO agendamento) {

        if(agendamento.medicoId() != null)
            return medicoRepository.getReferenceById(agendamento.medicoId());

        if(agendamento.especialidade() == null)
            throw new ValidationExceptionApi("Especialidade é obrigatória quando o médico não for escolhido!");

        return medicoRepository.pickMedicoRandomFreeDate(agendamento.especialidade(), agendamento.data());
    }


}
