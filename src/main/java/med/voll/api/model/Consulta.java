package med.voll.api.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.enuns.MotivoCancelamentoEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private MotivoCancelamentoEnum motivoCancelamento;

    public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime data) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.data = data;
    }

    public void cancel(MotivoCancelamentoEnum motivo) {
        this.motivoCancelamento = motivo;
    }
}
