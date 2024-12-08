package sptech.school.apizeporteiro.service.apartamento.dto;

import lombok.Data;
import sptech.school.apizeporteiro.domain.entrega.Entrega;

import java.time.LocalDate;
import java.util.List;

@Data
public class ApartamentoListagemDto {
    private Integer id;
    private String bloco;
    private String numAp;
    private boolean vazio;
    private CondominioDto condominio;
    private List<MoradorDto> moradores;
    private List<EntregaDto> entregas;

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

    @Data
    public static class EntregaDto {
        private Integer id;
        private String tipoEntrega;
        private String dataRecebimentoPorteiro;
        private String dataRecebimentoMorador;
        private boolean recebido;
    }


}
