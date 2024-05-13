package sptech.school.apizeporteiro.service.condominio.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CondominioCriacaoDto {
    @Size(min = 3, max = 20)
    private String nome;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
}
