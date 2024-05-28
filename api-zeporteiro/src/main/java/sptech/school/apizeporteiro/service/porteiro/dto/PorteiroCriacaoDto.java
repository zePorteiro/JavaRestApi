package sptech.school.apizeporteiro.service.porteiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PorteiroCriacaoDto {
    private String nome;
    private String rg;
    private String senha;
    private Integer condominioId;
}
