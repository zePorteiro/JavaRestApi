package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

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

        // Mapeia o apartamento usando o numAp
        Apartamento apartamento = new Apartamento();
        apartamento.setNumAp(dto.getNumAp());
        entrega.setApartamento(apartamento);

        return entrega;
    }

    public static EntregaListagemDto toDto(Entrega entity) {
        if (entity == null) return null;

        EntregaListagemDto entregaListagemDto = new EntregaListagemDto();

        entregaListagemDto.setDataRecebimentoPorteiro(entity.getDataRecebimentoPorteiro());
        entregaListagemDto.setDataRecebimentoMorador(entity.getDataRecebimentoMorador());
        entregaListagemDto.setRecebido(entity.getRecebido());

        // Mapeia o apartamento
        if (entity.getApartamento() != null) {
            EntregaListagemDto.ApartamentoDto apartamentoDto = new EntregaListagemDto.ApartamentoDto();
            apartamentoDto.setNumAp(entity.getApartamento().getNumAp());
            apartamentoDto.setBloco(entity.getApartamento().getBloco());
            entregaListagemDto.setApartamento(apartamentoDto);
        }
        entregaListagemDto.setPorteiro(toPorteiroDto(entity.getPorteiro()));

        return entregaListagemDto;
    }

    public static EntregaListagemDto.ApartamentoDto toApartamentoDto(Apartamento entity) {
        if (entity == null) return null;

        EntregaListagemDto.ApartamentoDto dto = new EntregaListagemDto.ApartamentoDto();

        dto.setNumAp(entity.getNumAp());
        dto.setBloco(entity.getBloco());

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
