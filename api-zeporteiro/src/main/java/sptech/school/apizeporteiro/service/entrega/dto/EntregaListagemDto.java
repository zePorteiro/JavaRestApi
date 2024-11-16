package sptech.school.apizeporteiro.service.entrega.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;

import java.time.LocalDate;

@Data
public class EntregaListagemDto {
    private String tipoEntrega;
    private LocalDate dataRecebimentoPorteiro;
    private LocalDate dataRecebimentoMorador;
    private Boolean recebido;

    private ApartamentoDto apartamento;
    private PorteiroDto porteiro;

    @Data
    public static class ApartamentoDto {
        private Integer id;
        private String numAp;
    }

    @Data
    public static class PorteiroDto {
        private Integer id;
        private String nome;
        private String rg;
    }
}

