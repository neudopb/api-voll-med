package med.voll.api.dto;

import med.voll.api.model.Endereco;
import med.voll.api.model.Paciente;

public record PacienteDetailDTO(Long id, String nome, String email, String cpf,
                                String telefome, Endereco endereco) {

    public PacienteDetailDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(),
                paciente.getTelefone(), paciente.getEndereco());
    }
}
