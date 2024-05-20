package sptech.school.apizeporteiro.service.porteiro.dto;

import lombok.Data;

@Data
public class PorteiroListagemDto {
    private Integer id;
    private String nome;
    private String rg;
    private String senha;

    private CondominioDto condominio;

    @Data
    public static class CondominioDto {
        private Integer id;
        private String nome;
        private String cep;
        private String logradouro;
        private String numero;
        private String bairro;
        private String cidade;
    }
}
