package sptech.school.apizeporteiro.service.morador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MoradorCriacaoDto {
    private String nome;
    private String numeroWhats1;
    private String numeroWhats2;
    private String numeroWhats3;
    private Integer fkApartamento;
}
