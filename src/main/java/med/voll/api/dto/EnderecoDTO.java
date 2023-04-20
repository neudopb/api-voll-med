package med.voll.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(
        @NotBlank
        String logradouro,
        @NotBlank
        String bairro,
        @NotBlank @Pattern(regexp = "[\\d-]{8,9}")
        String cep,
        @NotBlank
        String cidade,
        @NotBlank @Pattern(regexp = "\\w{2}")
        String uf,
        String complemento,
        String numero) {
}
