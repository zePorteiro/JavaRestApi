package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteTokenDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteCriacaoDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteListagemDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteAutenticacaoDto;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteMapper {

    public static ClienteListagemDto toDto(Cliente entity) {
        if (entity == null) return null;

        ClienteListagemDto dto = new ClienteListagemDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setNome(entity.getNome());
        dto.setCnpj(entity.getCnpj());
        dto.setRepresentante(entity.getRepresentante());


        // Mapeia os condomínios associados
        dto.setCondominios(entity.getCondominios().stream()
                .map(ClienteMapper::toCondominioDto)
                .collect(Collectors.toList()));

        return dto;
    }

    public static ClienteListagemDto.CondominioDto toCondominioDto(Condominio entity) {
        if (entity == null) return null;

        ClienteListagemDto.CondominioDto condominioDto = new ClienteListagemDto.CondominioDto();
        condominioDto.setId(entity.getId());
        condominioDto.setNome(entity.getNome());
        condominioDto.setCep(entity.getCep());
        condominioDto.setLogradouro(entity.getLogradouro());
        condominioDto.setNumero(entity.getNumero());
        condominioDto.setBairro(entity.getBairro());
        condominioDto.setCidade(entity.getCidade());

        return condominioDto;
    }

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

    public static List<ClienteListagemDto> toDto(List<Cliente> entidade) {
        return entidade.stream().map(ClienteMapper::toDto).collect(Collectors.toList());
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

    public static ClienteAutenticacaoDto autenticacaoDto(Cliente cliente, String token) {
        ClienteAutenticacaoDto clienteTokenDto = new ClienteAutenticacaoDto();

        clienteTokenDto.setUserId(cliente.getId());
        clienteTokenDto.setEmail(cliente.getEmail());
        clienteTokenDto.setNome(cliente.getNome());
        clienteTokenDto.setToken(token);

        if (cliente.getCondominios() != null && !cliente.getCondominios().isEmpty()) {
            clienteTokenDto.setCondominioid(cliente.getCondominios().get(0).getId());
        } else {
            clienteTokenDto.setCondominioid(null);
        }

        return clienteTokenDto;
    }


}
