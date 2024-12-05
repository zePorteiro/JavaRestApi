package sptech.school.apizeporteiro.service.morador.dto;

import lombok.Data;

@Data
public class MoradorListagemDto {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String cep;
    private ApartamentoDto apartamento;

    @Data
    public static class ApartamentoDto {
        private Integer id;
        private String numAp;
    }
}
