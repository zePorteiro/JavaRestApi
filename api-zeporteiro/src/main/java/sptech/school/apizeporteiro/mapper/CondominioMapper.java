package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioListagemDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.util.ArrayList;
import java.util.List;

public class CondominioMapper {
    public static Condominio toEntity(CondominioCriacaoDto dto) {
        if (dto == null) {
            return null;
        }

        Condominio condominio = new Condominio();

        condominio.setNome(dto.getNome());
        condominio.setCep(dto.getCep());
        condominio.setLogradouro(dto.getLogradouro());
        condominio.setNumero(dto.getNumero());
        condominio.setBairro(dto.getBairro());
        condominio.setCidade(dto.getCidade());
        // vão ter as fk?

        return condominio;
    }

    public static CondominioListagemDto toDto(Condominio entity) {
        if (entity == null) {
            return null;
        }

       CondominioListagemDto condominioListagemDto = new CondominioListagemDto();

        condominioListagemDto.setNome(entity.getNome());
        condominioListagemDto.setCep(entity.getCep());
        condominioListagemDto.setLogradouro(entity.getLogradouro());
        condominioListagemDto.setNumero(entity.getNumero());
        condominioListagemDto.setBairro(entity.getBairro());
        condominioListagemDto.setCidade(entity.getCidade());

        return condominioListagemDto;
    }

    public static List<CondominioListagemDto> toDto(List<Condominio> entidade) {
        List<CondominioListagemDto> dtos = new ArrayList<>();
        for (Condominio e : entidade) {
            dtos.add(toDto(e));
        }
        return dtos;
    }
}
