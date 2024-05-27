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
    private Boolean vazio;
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
        private LocalDate dataRecebimentoPorteiro;
        private LocalDate dataRecebimentoMorador;
        private Boolean recebido;
    }
}
