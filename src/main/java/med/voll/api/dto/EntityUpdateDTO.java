package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;

public record EntityUpdateDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoDTO endereco) {
}
