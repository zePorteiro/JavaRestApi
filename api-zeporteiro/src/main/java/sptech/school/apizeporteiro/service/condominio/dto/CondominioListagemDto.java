package sptech.school.apizeporteiro.service.condominio.dto;

import lombok.Data;

@Data
public class CondominioListagemDto {
    private Integer id;
    private String nome;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
}
