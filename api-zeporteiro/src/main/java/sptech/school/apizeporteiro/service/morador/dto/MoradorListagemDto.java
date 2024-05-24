package sptech.school.apizeporteiro.service.morador.dto;

import lombok.Data;

@Data
public class MoradorListagemDto {
    private Integer id;
    private String nome;
    private String numeroWhats1;
    private String numeroWhats2;
    private String numeroWhats3;

    private ApartamentoDto apartamento;

    @Data
    public static class ApartamentoDto {
        private Integer id;
        private String bloco;
        private String numAp;
        private Boolean vazio;
    }
}
