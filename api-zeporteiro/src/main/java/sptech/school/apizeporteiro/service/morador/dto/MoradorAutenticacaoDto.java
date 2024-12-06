package sptech.school.apizeporteiro.service.morador.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoradorAutenticacaoDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;
}
