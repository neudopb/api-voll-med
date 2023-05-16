package med.voll.api.validation.cancellation;

import med.voll.api.dto.CancelamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationExceptionApi;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidatorAntecedentTimeCancelamento")
public class ValidatorAntecedentTime implements ValidatorCancelamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validate(CancelamentoConsultaDTO cancelamento) {
        var consulta = repository.getReferenceById(cancelamento.consultaId());
        var now = LocalDateTime.now();
        var differenceHours = Duration.between(now, consulta.getData()).toHours();

        if(differenceHours < 24)
            throw new ValidationExceptionApi("Consulta somente pode ser cancelada com antecedência mínima de 24 horas!");
    }
}
