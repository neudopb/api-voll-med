package med.voll.api.dto;

import med.voll.api.enuns.EspecialidadeEnum;
import med.voll.api.model.Endereco;
import med.voll.api.model.Medico;

public record MedicoDetailDTO(Long id, String nome, String email, String crm,
                              String telefone, EspecialidadeEnum especialidade, Endereco endereco) {

    public MedicoDetailDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(),
                medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
