package med.voll.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(
        @NotBlank @Email
        String login,
        @NotBlank
        String senha) {

}
