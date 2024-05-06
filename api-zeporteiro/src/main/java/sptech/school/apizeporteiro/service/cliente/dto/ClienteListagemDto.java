package sptech.school.apizeporteiro.service.cliente.dto;

import lombok.Data;

@Data
public class ClienteListagemDto {
    private Integer id;
    private String email;
    private String nome;
    private String cnpj;
    private String representante;
}
