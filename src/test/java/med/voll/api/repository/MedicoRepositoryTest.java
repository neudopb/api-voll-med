package med.voll.api.repository;

import med.voll.api.dto.EnderecoDTO;
import med.voll.api.dto.MedicoDTO;
import med.voll.api.dto.PacienteDTO;
import med.voll.api.enuns.EspecialidadeEnum;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.model.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve devolver null quando o unico medico cadastrado nao esta disponivel na data")
    void pickMedicoRandomFreeDateTest1() {

        //given or arrange
        var nextMonday10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = saveMedico("Medico", "medico@voll.med", "654321", EspecialidadeEnum.CARDIOLOGIA);
        var paciente = savePaciente("Paciente", "paciente@google.com", "99999999999");
        saveConsulta(medico, paciente, nextMonday10);

        //when or act
        var medicoFree = medicoRepository.pickMedicoRandomFreeDate(EspecialidadeEnum.CARDIOLOGIA, nextMonday10);

        //then or assert
        assertThat(medicoFree).isNull();
    }

    @Test
    @DisplayName("Deve devolver medico quando ele estiver disponivel na data")
    void pickMedicoRandomFreeDateTest2() {

        //given or arrange
        var nextMonday10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = saveMedico("Medico", "medico@voll.med", "654321", EspecialidadeEnum.CARDIOLOGIA);

        //when or act
        var medicoFree = medicoRepository.pickMedicoRandomFreeDate(EspecialidadeEnum.CARDIOLOGIA, nextMonday10);

        //then or assert
        assertThat(medicoFree).isEqualTo(medico);
    }

    private Consulta saveConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        var consulta = new Consulta(null, medico, paciente, data);
        em.persist(consulta);
        return consulta;
    }

    private Medico saveMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
        var medico = new Medico(medicoDTO(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente savePaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(pacienteDTO(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private MedicoDTO medicoDTO(String nome, String email, String crm, EspecialidadeEnum especialidade) {
        return new MedicoDTO(
                nome,
                email,
                "84999999999",
                crm,
                especialidade,
                enderecoDTO()
        );
    }

    private PacienteDTO pacienteDTO(String nome, String email, String cpf) {
        return new PacienteDTO(
                nome,
                email,
                cpf,
                "84999999999",
                enderecoDTO()
        );
    }

    private EnderecoDTO enderecoDTO() {
        return new EnderecoDTO(
                "S. Lagoa dos Milhomens",
                "Serra Santana",
                "63430000",
                "Icó",
                "CE",
                "Zona Rural",
                "34"
        );
    }

}