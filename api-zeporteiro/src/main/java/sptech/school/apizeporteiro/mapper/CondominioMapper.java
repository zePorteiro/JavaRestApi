package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioListagemDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CondominioMapper {

    public static CondominioListagemDto toDto(Condominio entity) {
        if (entity == null) return null;

        CondominioListagemDto dto = new CondominioListagemDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCep(entity.getCep());
        dto.setLogradouro(entity.getLogradouro());
        dto.setNumero(entity.getNumero());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());

        dto.setCliente(toClienteDto(entity.getCliente()));
        dto.setApartamentos(entity.getApartamentos().stream()
                .map(CondominioMapper::toApartamentoDto)
                .collect(Collectors.toList()));

        return dto;
    }

    private static CondominioListagemDto.ClienteDto toClienteDto(Cliente entity) {
        if (entity == null) return null;

        CondominioListagemDto.ClienteDto clienteDto = new CondominioListagemDto.ClienteDto();
        clienteDto.setId(entity.getId());
        clienteDto.setEmail(entity.getEmail());
        clienteDto.setNome(entity.getNome());
        // Removido o campo senha
        clienteDto.setCnpj(entity.getCnpj());
        clienteDto.setRepresentante(entity.getRepresentante());

        return clienteDto;
    }

    private static CondominioListagemDto.ApartamentoDto toApartamentoDto(Apartamento entity) {
        if (entity == null) return null;

        CondominioListagemDto.ApartamentoDto apartamentoDto = new CondominioListagemDto.ApartamentoDto();
        apartamentoDto.setId(entity.getId());
        apartamentoDto.setBloco(entity.getBloco());
        apartamentoDto.setNumAp(entity.getNumAp());
        apartamentoDto.setVazio(entity.isVazio());

        return apartamentoDto;
    }

    public static Condominio toEntity(CondominioCriacaoDto dto) {
        if (dto == null) return null;

        Condominio condominio = new Condominio();
        condominio.setNome(dto.getNome());
        condominio.setCep(dto.getCep());
        condominio.setLogradouro(dto.getLogradouro());
        condominio.setNumero(dto.getNumero());
        condominio.setBairro(dto.getBairro());
        condominio.setCidade(dto.getCidade());

        Cliente cliente = new Cliente();
        cliente.setId(dto.getClienteId());
        condominio.setCliente(cliente);

        return condominio;
    }

    public static List<CondominioListagemDto> toDto(List<Condominio> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(CondominioMapper::toDto)
                .collect(Collectors.toList());
    }
}

