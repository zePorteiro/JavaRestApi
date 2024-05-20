package sptech.school.apizeporteiro.service.porteiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PorteiroCriacaoDto {
    @NotBlank
    @NotNull
    private String nome;
    @NotBlank
    @NotNull
    private String rg;
    @NotBlank
    @NotNull
    private String senha;
}
