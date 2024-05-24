package sptech.school.apizeporteiro.service.apartamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApartamentoCriacaoDto {
    @NotBlank
    @NotNull
    private String bloco;
    @NotBlank
    @NotNull
    private String numAp;
    @NotBlank
    @NotNull
    private boolean vazio;
    private Integer condominioId;
}
