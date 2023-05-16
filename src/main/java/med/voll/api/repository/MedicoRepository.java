package med.voll.api.repository;

import med.voll.api.enuns.EspecialidadeEnum;
import med.voll.api.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable page);

    Medico getReferenceByIdAndAtivoTrue(Long id);

    @Query("""
            SELECT m FROM Medico m 
            WHERE m.ativo = 1
            AND m.especialidade = :especialidade
            AND m.id NOT IN(
                SELECT c.medico.id FROM Consulta c
                WHERE c.data = :data
            )
            ORDER BY rand()
            LIMIT 1
            """)
    Medico pickMedicoRandomFreeDate(EspecialidadeEnum especialidade, LocalDateTime data);

}
