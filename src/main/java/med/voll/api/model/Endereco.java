package med.voll.api.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.dto.EnderecoDTO;

@Entity
@Table(name = "enderecos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Endereco(EnderecoDTO enderecoDTO) {
        this.logradouro = enderecoDTO.logradouro();
        this.bairro = enderecoDTO.bairro();
        this.cep = enderecoDTO.cep();
        this.numero = enderecoDTO.numero();
        this.complemento = enderecoDTO.complemento();
        this.cidade = enderecoDTO.cidade();
        this.uf = enderecoDTO.uf();
    }

    public void updateInformations(EnderecoDTO endereco) {
        if(endereco.logradouro() != null)
            this.logradouro = endereco.logradouro();

        if(endereco.bairro() != null)
            this.bairro = endereco.bairro();


        if(endereco.cep() != null)
            this.cep = endereco.cep();


        if(endereco.numero() != null)
            this.numero = endereco.numero();


        if(endereco.complemento() != null)
            this.complemento = endereco.complemento();


        if(endereco.cidade() != null)
            this.cidade = endereco.cidade();


        if(endereco.uf() != null)
            this.uf = endereco.uf();

    }
}
