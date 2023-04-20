package med.voll.api.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.dto.EntityUpdateDTO;
import med.voll.api.dto.MedicoDTO;
import med.voll.api.enuns.EspecialidadeEnum;

@Entity
@Table(name = "medicos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private EspecialidadeEnum especialidade;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    private Boolean ativo;

    public Medico(MedicoDTO medicoDTO) {
        this.ativo = true;
        this.nome = medicoDTO.nome();
        this.email = medicoDTO.email();
        this.telefone = medicoDTO.telefone();
        this.crm = medicoDTO.crm();
        this.especialidade = medicoDTO.especialidade();
        this.endereco = new Endereco(medicoDTO.endereco());
    }

    public void updateInformations(EntityUpdateDTO medico) {
        if(medico.nome() != null)
            this.nome = medico.nome();

        if(medico.telefone() != null)
            this.telefone = medico.telefone();

        if(medico.endereco() != null)
            this.endereco.updateInformations(medico.endereco());
    }

    public void inactivate() {
        this.ativo = false;
    }
}
