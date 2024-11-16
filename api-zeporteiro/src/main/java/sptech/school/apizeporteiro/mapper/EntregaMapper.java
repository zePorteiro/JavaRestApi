package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntregaMapper {
    public static Entrega toEntity(EntregaCriacaoDto dto) {
        if (dto == null) {
            return null;
        }

        Entrega entrega = new Entrega();

        entrega.setTipoEntrega(dto.getTipoEntrega());
        entrega.setDataRecebimentoPorteiro(dto.getDataRecebimentoPorteiro());
        entrega.setDataRecebimentoMorador(dto.getDataRecebimentoMorador());
        entrega.setRecebido(dto.getRecebido());

        return entrega;
    }

    public static EntregaListagemDto toDto(Entrega entrega) {
        if (entrega == null) {
            return null;
        }

        EntregaListagemDto dto = new EntregaListagemDto();
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

    public static List<EntregaListagemDto> toListDto(List<Entrega> entregas) {
        if (entregas == null) {
            return Collections.emptyList();
        }
        return entregas.stream()
                .map(EntregaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static EntregaListagemDto.ApartamentoDto toApartamentoDto(Apartamento entity) {
        if (entity == null) return null;

        EntregaListagemDto.ApartamentoDto dto = new EntregaListagemDto.ApartamentoDto();
        dto.setId(entity.getId());
        dto.setNumAp(entity.getNumAp());

        return dto;
    }

    public static EntregaListagemDto.PorteiroDto toPorteiroDto(Porteiro entity) {
        if (entity == null) return null;

        EntregaListagemDto.PorteiroDto dto = new EntregaListagemDto.PorteiroDto();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setRg(entity.getRg());

        return dto;
    }
}
