package sptech.school.apizeporteiro.mapper;

import org.hibernate.boot.registry.classloading.spi.ClassLoadingException;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.service.morador.autenticacao.dto.MoradorDetalhesDto;
import sptech.school.apizeporteiro.service.morador.autenticacao.dto.MoradorTokenDto;
import sptech.school.apizeporteiro.service.morador.dto.CadastroMoradorDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorCriacaoDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorResponseDto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MoradorMapper {

    public static MoradorListagemDto toDto(Morador entity) {
        if (entity == null) return null;

        MoradorListagemDto dto = new MoradorListagemDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setCep(entity.getCep());

        if (entity.getApartamento() != null) {
            dto.setApartamento(toApartamentoDto(entity.getApartamento()));
        }

        return dto;
    }

    private static MoradorListagemDto.ApartamentoDto toApartamentoDto(Apartamento entity) {
        if (entity == null) return null;

        MoradorListagemDto.ApartamentoDto apartamentoDto = new MoradorListagemDto.ApartamentoDto();
        apartamentoDto.setId(entity.getId());
        apartamentoDto.setNumAp(entity.getNumAp());

        return apartamentoDto;
    }

    public static Morador toEntity(CadastroMoradorDto dto, Condominio condominio, Apartamento apartamento) {
        if (dto == null) return null;

        Morador morador = new Morador();
        morador.setNome(dto.getNome());
        morador.setEmail(dto.getEmail());
        morador.setSenha(dto.getSenha());
        morador.setTelefone(dto.getTelefone());
        morador.setCep(dto.getCep());
        morador.setCondominio(condominio);
        morador.setApartamento(apartamento);

        return morador;
    }

    public static List<MoradorListagemDto> toDtoList(List<Morador> entities) {
        if (entities == null) return Collections.emptyList();

        return entities.stream()
                .map(MoradorMapper::toDto)
                .collect(Collectors.toList());
    }

    public static MoradorTokenDto of(Morador morador, String token) {
        MoradorTokenDto moradorTokenDto = new MoradorTokenDto();
        moradorTokenDto.setUserId(morador.getId());
        moradorTokenDto.setEmail(morador.getEmail());
        moradorTokenDto.setNome(morador.getNome());
        moradorTokenDto.setToken(token);
        return moradorTokenDto;
    }

    public static MoradorResponseDto toResponseDto(Morador morador) {
        if (morador == null) {
            return null;
        }
        return new MoradorResponseDto(
                morador.getId(),
                morador.getNome(),
                morador.getEmail(),
                morador.getApartamento()
        );
    }
}