package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteTokenDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteCriacaoDto;
import sptech.school.apizeporteiro.service.porteiro.autenticacao.dto.PorteiroTokenDto;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroCriacaoDto;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroListagemDto;

import javax.sound.sampled.Port;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PorteiroMapper {

    public static Porteiro toEntity(PorteiroCriacaoDto dto) {
        if (dto == null) {
            return null;
        }
        Porteiro porteiro = new Porteiro();
        porteiro.setNome(dto.getNome());
        porteiro.setRg(dto.getRg());
        porteiro.setSenha(dto.getSenha());
        return porteiro;
    }

    public static PorteiroListagemDto toDto(Porteiro entity) {
        if (entity == null) {
            return null;
        }
        PorteiroListagemDto dto = new PorteiroListagemDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setRg(entity.getRg());
        dto.setSenha(entity.getSenha());
        dto.setCondominio(toCondominioDto(entity.getCondominio()));
        dto.setEntregas(toEntregaDto(entity.getEntregas()));
        return dto;
    }

    public static List<PorteiroListagemDto.EntregaDto> toEntregaDto(List<Entrega> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(entrega -> {
            PorteiroListagemDto.EntregaDto dto = new PorteiroListagemDto.EntregaDto();
            dto.setId(entrega.getId());
            dto.setTipoEntrega(entrega.getTipoEntrega());
            dto.setDataRecebimentoPorteiro(entrega.getDataRecebimentoPorteiro());
            dto.setDataRecebimentoMorador(entrega.getDataRecebimentoMorador());
            return dto;
        }).collect(Collectors.toList());
    }

    private static PorteiroListagemDto.CondominioDto toCondominioDto(Condominio entity) {
        if (entity == null) {
            return null;
        }
        PorteiroListagemDto.CondominioDto dto = new PorteiroListagemDto.CondominioDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        return dto;
    }
}

