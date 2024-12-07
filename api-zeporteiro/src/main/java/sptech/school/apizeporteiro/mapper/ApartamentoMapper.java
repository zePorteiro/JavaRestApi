package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoCriacaoDto;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoListagemDto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ApartamentoMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ApartamentoListagemDto toDto(Apartamento entity) {
        if (entity == null) return null;

        ApartamentoListagemDto dto = new ApartamentoListagemDto();
        dto.setId(entity.getId());
        dto.setBloco(entity.getBloco());
        dto.setNumAp(entity.getNumAp());
        dto.setVazio(entity.isVazio());
        dto.setCondominio(toDtoCondominio(entity.getCondominio()));
        dto.setMoradores(entity.getMoradores() != null ?
                entity.getMoradores().stream()
                        .map(ApartamentoMapper::toDtoMorador)
                        .collect(Collectors.toList())
                : null);
        dto.setEntregas(entity.getEntregas() != null ?
                entity.getEntregas().stream()
                        .map(ApartamentoMapper::toDtoEntrega)
                        .collect(Collectors.toList())
                : null);

        return dto;
    }

    public static ApartamentoListagemDto.CondominioDto toDtoCondominio(Condominio entity) {
        if (entity == null) return null;

        ApartamentoListagemDto.CondominioDto dto = new ApartamentoListagemDto.CondominioDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());

        return dto;
    }

    public static ApartamentoListagemDto.MoradorDto toDtoMorador(Morador entity) {
        if (entity == null) return null;

        ApartamentoListagemDto.MoradorDto dto = new ApartamentoListagemDto.MoradorDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());

        return dto;
    }

    private static ApartamentoListagemDto.EntregaDto toDtoEntrega(Entrega entity) {
        if (entity == null) return null;

        ApartamentoListagemDto.EntregaDto dto = new ApartamentoListagemDto.EntregaDto();
        dto.setId(entity.getId());
        dto.setTipoEntrega(entity.getTipoEntrega());
        dto.setDataRecebimentoPorteiro(entity.getDataRecebimentoPorteiro().format(formatter));
        dto.setDataRecebimentoMorador(entity.getDataRecebimentoMorador() != null ? entity.getDataRecebimentoMorador().format(formatter) : null);
        dto.setRecebido(entity.getRecebido());

        return dto;
    }

    public static Apartamento toEntity(ApartamentoCriacaoDto dto) {
        if (dto == null) return null;

        Apartamento apartamento = new Apartamento();
        apartamento.setBloco(dto.getBloco());
        apartamento.setNumAp(dto.getNumAp());
        apartamento.setVazio(dto.getVazio());

        Condominio condominio = new Condominio();
        condominio.setId(dto.getCondominioId());
        apartamento.setCondominio(condominio);

        return apartamento;
    }

    public static List<ApartamentoListagemDto> toDtoList(List<Apartamento> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(ApartamentoMapper::toDto)
                .collect(Collectors.toList());
    }
}