package sptech.school.apizeporteiro.service.morador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MoradorCriacaoDto {
    @NotBlank
    @NotNull
    private String nome;
    @NotBlank
    @NotNull
    private String numeroWhats1;
    @NotBlank
    @NotNull
    private String numeroWhats2;
    @NotBlank
    @NotNull
    private String numeroWhats3;
}
