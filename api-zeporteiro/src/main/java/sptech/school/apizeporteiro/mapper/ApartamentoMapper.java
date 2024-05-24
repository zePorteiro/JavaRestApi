package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoCriacaoDto;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoListagemDto;

import java.util.List;

public class ApartamentoMapper {

    public static ApartamentoListagemDto toDto(Apartamento entity) {
        if (entity == null) return null;

        ApartamentoListagemDto dto = new ApartamentoListagemDto();

        dto.setId(entity.getId());
        dto.setBloco(entity.getBloco());
        dto.setNumAp(entity.getNumAp());
        dto.setVazio(entity.isVazio());
        dto.setCondominio(toDtoCondominio(entity.getCondominio()));
        dto.setMoradores(toDtoMorador(entity.getMoradores()));

        return dto;
    }

    public static ApartamentoListagemDto.CondominioDto toDtoCondominio(Condominio entity) {
        if (entity == null) return null;

        ApartamentoListagemDto.CondominioDto dto = new ApartamentoListagemDto.CondominioDto();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());

        return dto;
    }

    public static List<ApartamentoListagemDto.MoradorDto> toDtoMorador(List<Morador> entities) {
        return entities.stream().map(f -> {

            ApartamentoListagemDto.MoradorDto dto = new ApartamentoListagemDto.MoradorDto();

            dto.setId(f.getId());
            dto.setNome(f.getNome());

            return dto;
        }).toList();
    }

    public static Apartamento toEntity(ApartamentoCriacaoDto dto) {
        if (dto == null) return null;

        Apartamento apartamento = new Apartamento();

        apartamento.setBloco(dto.getBloco());
        apartamento.setNumAp(dto.getNumAp());
        apartamento.setVazio(dto.isVazio());

        Condominio condominio = new Condominio();
        condominio.setId(dto.getCondominioId());
        apartamento.setCondominio(condominio);

        return apartamento;
    }
}
