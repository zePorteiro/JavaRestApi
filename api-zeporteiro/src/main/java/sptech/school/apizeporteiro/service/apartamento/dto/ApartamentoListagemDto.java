package sptech.school.apizeporteiro.service.apartamento.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApartamentoListagemDto {
    private Integer id;
    private String bloco;
    private String numAp;
    private Boolean vazio;
    private CondominioDto condominio;
    private List<MoradorDto> moradores;

    @Data
    public static class CondominioDto {
        private Integer id;
        private String nome;
    }

    @Data
    public static class MoradorDto {
        private Integer id;
        private String nome;
    }
}
