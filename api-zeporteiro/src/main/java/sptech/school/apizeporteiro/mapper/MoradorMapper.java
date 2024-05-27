package sptech.school.apizeporteiro.mapper;

import org.hibernate.boot.registry.classloading.spi.ClassLoadingException;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.service.morador.dto.MoradorCriacaoDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;

import java.util.List;
import java.util.stream.Collectors;

public class MoradorMapper {

    public static MoradorListagemDto toDto(Morador entity) {
        if (entity == null) return null;

        MoradorListagemDto dto = new MoradorListagemDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setNumeroWhats1(entity.getNumeroWhats1());
        dto.setNumeroWhats2(entity.getNumeroWhats2());
        dto.setNumeroWhats3(entity.getNumeroWhats3());

        dto.setApartamento(toApartamentoDto(entity.getApartamento()));

        return dto;
    }

    private static MoradorListagemDto.ApartamentoDto toApartamentoDto(Apartamento entity) {
        if (entity == null) return null;

        MoradorListagemDto.ApartamentoDto apartamentoDto = new MoradorListagemDto.ApartamentoDto();
        apartamentoDto.setId(entity.getId());
        apartamentoDto.setBloco(entity.getBloco());
        apartamentoDto.setNumAp(entity.getNumAp());
        apartamentoDto.setVazio(entity.isVazio());

        return apartamentoDto;
    }

    public static Morador toEntity(MoradorCriacaoDto dto) {
        if (dto == null) return null;

        Morador morador = new Morador();
        morador.setNome(dto.getNome());
        morador.setNumeroWhats1(dto.getNumeroWhats1());
        morador.setNumeroWhats2(dto.getNumeroWhats2());
        morador.setNumeroWhats3(dto.getNumeroWhats3());

        return morador;
    }

    public static List<Morador> toEntityList(List<MoradorCriacaoDto> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(MoradorMapper::toEntity)
                .collect(Collectors.toList());
    }
}
