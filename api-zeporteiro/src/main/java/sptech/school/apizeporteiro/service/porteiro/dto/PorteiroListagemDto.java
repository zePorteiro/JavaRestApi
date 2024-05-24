package sptech.school.apizeporteiro.service.porteiro.dto;

import lombok.Data;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;

import java.time.LocalDate;
import java.util.List;

@Data
public class PorteiroListagemDto {
    private Integer id;
    private String nome;
    private String rg;
    private String senha;
    private List<EntregaDto> entregas;
    private CondominioDto condominio;

    @Data
    public static class CondominioDto {
        private Integer id;
        private String nome;
    }
    @Data
    public static class EntregaDto {
        private Integer id;
        private String tipoEntrega;
        private LocalDate dataRecebimentoPorteiro;
        private LocalDate dataRecebimentoMorador;
    }
}
