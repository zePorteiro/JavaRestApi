package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteTokenDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteCriacaoDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteListagemDto;

import java.util.ArrayList;
import java.util.List;

public class ClienteMapper {
    public static Cliente toEntity(ClienteCriacaoDto dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(dto.getSenha());
        cliente.setCnpj(dto.getCnpj());
        cliente.setRepresentante(dto.getRepresentante());

        return cliente;
    }

    public static ClienteListagemDto toDto(Cliente entidade) {
        if (entidade == null) return null;

        ClienteListagemDto listagemDto = new ClienteListagemDto();
        listagemDto.setId(entidade.getId());
        listagemDto.setEmail(entidade.getEmail());
        listagemDto.setNome(entidade.getNome());
        listagemDto.setCnpj(entidade.getCnpj());
        listagemDto.setRepresentante(entidade.getRepresentante());

        return listagemDto;
    }

    public static List<ClienteListagemDto> toDto(List<Cliente> entidade) {
        List<ClienteListagemDto> dtos = new ArrayList<>();
        for (Cliente e : entidade) {
            dtos.add(toDto(e));
        }
        return dtos;
    }
    public static Cliente of(ClienteCriacaoDto clienteCriacaoDto){
        Cliente cliente = new Cliente();

        cliente.setEmail(clienteCriacaoDto.getEmail());
        cliente.setNome(clienteCriacaoDto.getNome());
        cliente.setSenha(clienteCriacaoDto.getSenha());
        cliente.setCnpj(clienteCriacaoDto.getCnpj());
        cliente.setRepresentante(clienteCriacaoDto.getRepresentante());

        return cliente;
    }

    public static ClienteTokenDto of(Cliente cliente, String token){
        ClienteTokenDto clienteTokenDto = new ClienteTokenDto();

        clienteTokenDto.setUserId(cliente.getId());
        clienteTokenDto.setEmail(cliente.getEmail());
        clienteTokenDto.setNome(cliente.getNome());
        clienteTokenDto.setToken(token);

        return clienteTokenDto;
    }
}
