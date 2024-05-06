package sptech.school.apizeporteiro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoApiExternaDto {
    private String cep;

    @JsonProperty(value = "uf")
    private String estado;

    @JsonProperty(value = "localidade")
    private String cidade;

    private String bairro;

    @JsonProperty(value = "logradouro")
    private String rua;
}
