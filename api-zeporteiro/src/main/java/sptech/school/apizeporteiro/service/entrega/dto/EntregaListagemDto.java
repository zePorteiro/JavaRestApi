package sptech.school.apizeporteiro.service.entrega.dto;

import lombok.Data;
import sptech.school.apizeporteiro.domain.entrega.Entrega;

import java.time.LocalDate;

@Data
public class EntregaListagemDto {
    private Integer id; // Adicionado campo id
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

    public static EntregaListagemDto toDto(Entrega entrega) {
        if (entrega == null) {
            return null;
        }

        EntregaListagemDto dto = new EntregaListagemDto();
        dto.setId(entrega.getId()); // Certifique-se de que o ID está sendo mapeado
        dto.setTipoEntrega(entrega.getTipoEntrega());
        dto.setDataRecebimentoPorteiro(entrega.getDataRecebimentoPorteiro());
        dto.setDataRecebimentoMorador(entrega.getDataRecebimentoMorador());
        dto.setRecebido(entrega.getRecebido());

        if (entrega.getApartamento() != null) {
            EntregaListagemDto.ApartamentoDto apartamentoDto = new EntregaListagemDto.ApartamentoDto();
            apartamentoDto.setId(entrega.getApartamento().getId());
            apartamentoDto.setNumAp(entrega.getApartamento().getNumAp());
            dto.setApartamento(apartamentoDto);
        }

        if (entrega.getPorteiro() != null) {
            EntregaListagemDto.PorteiroDto porteiroDto = new EntregaListagemDto.PorteiroDto();
            porteiroDto.setId(entrega.getPorteiro().getId());
            porteiroDto.setNome(entrega.getPorteiro().getNome());
            porteiroDto.setRg(entrega.getPorteiro().getRg());
            dto.setPorteiro(porteiroDto);
        }

        return dto;
    }
}