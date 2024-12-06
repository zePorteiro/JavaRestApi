package sptech.school.apizeporteiro.service.morador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorResponseDto {
    private Integer id;
    private String nome;
    private String email;
    private Apartamento apartamento;
}
