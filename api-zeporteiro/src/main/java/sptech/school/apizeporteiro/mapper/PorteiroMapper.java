package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroCriacaoDto;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroListagemDto;

import javax.sound.sampled.Port;

public class PorteiroMapper {

    public static PorteiroListagemDto toDto(Porteiro entity) {
        if (entity == null) return null;

        PorteiroListagemDto dto = new PorteiroListagemDto();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setRg(entity.getRg());
        dto.setSenha(entity.getSenha());

        dto.setCondominio(toCondominioDto(entity.getCondominio()));

        return dto;
    }

    private static PorteiroListagemDto.CondominioDto toCondominioDto(Condominio entity) {
        if(entity == null) return null;

        PorteiroListagemDto.CondominioDto condominioDto = new PorteiroListagemDto.CondominioDto();
        condominioDto.setId(entity.getId());
        condominioDto.setNome(entity.getNome());
        condominioDto.setCep(entity.getCep());
        condominioDto.setLogradouro(entity.getLogradouro());
        condominioDto.setNumero(entity.getNumero());
        condominioDto.setBairro(entity.getBairro());
        condominioDto.setCidade(entity.getCidade());

        return condominioDto;
    }

    public static Porteiro toEntity(PorteiroCriacaoDto dto) {
        if(dto == null) return null;

        Porteiro porteiro = new Porteiro();
        porteiro.setNome(dto.getNome());
        porteiro.setRg(dto.getRg());
        porteiro.setSenha(dto.getSenha());

        return porteiro;
    }
}
