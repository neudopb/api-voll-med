package med.voll.api.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.dto.EntityUpdateDTO;
import med.voll.api.dto.PacienteDTO;

@Entity
@Table(name = "pacientes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    private Boolean ativo;

    public Paciente(PacienteDTO pacienteDTO) {
        this.ativo = true;
        this.nome = pacienteDTO.nome();
        this.email = pacienteDTO.email();
        this.cpf = pacienteDTO.cpf();
        this.telefone = pacienteDTO.telefone();
        this.endereco = new Endereco(pacienteDTO.endereco());
    }

    public void updateInformations(EntityUpdateDTO paciente) {
        if(paciente.nome() != null)
            this.nome = paciente.nome();

        if(paciente.telefone() != null)
            this.telefone = paciente.telefone();

        if(paciente.endereco() != null)
            this.endereco.updateInformations(paciente.endereco());
    }

    public void inactivate() {
        this.ativo = false;
    }
}
