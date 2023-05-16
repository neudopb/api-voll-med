package med.voll.api.repository;

import med.voll.api.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByMedicoIdAndData(Long medicoId, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long pacienteId, LocalDateTime firstHours, LocalDateTime lastHours);
}
