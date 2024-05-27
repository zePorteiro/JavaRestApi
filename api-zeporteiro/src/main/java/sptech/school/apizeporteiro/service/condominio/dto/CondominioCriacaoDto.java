package sptech.school.apizeporteiro.service.condominio.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CondominioCriacaoDto {
    private String nome;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private Integer fkCliente;
}
