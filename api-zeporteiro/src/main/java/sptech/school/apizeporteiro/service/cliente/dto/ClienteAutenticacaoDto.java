package sptech.school.apizeporteiro.service.cliente.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteAutenticacaoDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;

    private Integer condominioid;
}
