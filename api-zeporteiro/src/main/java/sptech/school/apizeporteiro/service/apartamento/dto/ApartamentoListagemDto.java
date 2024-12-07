package sptech.school.apizeporteiro.service.apartamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApartamentoListagemDto {
    private Integer id;
    private String bloco;
    private String numAp;
    private boolean vazio;
    private CondominioDto condominio;
    private List<MoradorDto> moradores;
    private List<EntregaDto> entregas;

    @Getter
    @Setter
    public static class CondominioDto {
        private Integer id;
        private String nome;
    }

    @Getter
    @Setter
    public static class MoradorDto {
        private Integer id;
        private String nome;
    }

    @Getter
    @Setter
    public static class EntregaDto {
        private Integer id;
        private String tipoEntrega;
        private String dataRecebimentoPorteiro;
        private String dataRecebimentoMorador;
        private boolean recebido;
    }
}