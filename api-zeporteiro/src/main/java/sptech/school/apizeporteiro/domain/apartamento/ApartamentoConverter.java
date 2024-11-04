package sptech.school.apizeporteiro.domain.apartamento;

import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoListagemDto;

import java.util.List;
import java.util.stream.Collectors;

public class ApartamentoConverter {

    public static ApartamentoListagemDto toDto(Apartamento apartamento) {
        ApartamentoListagemDto dto = new ApartamentoListagemDto();
        dto.setId(apartamento.getId());
        dto.setBloco(apartamento.getBloco());

        // Conversão do Condominio
        if (apartamento.getCondominio() != null) {
            ApartamentoListagemDto.CondominioDto condominioDto = new ApartamentoListagemDto.CondominioDto();
            condominioDto.setId(apartamento.getCondominio().getId());
            condominioDto.setNome(apartamento.getCondominio().getNome());
            dto.setCondominio(condominioDto);
        }

        // Conversão dos moradores
        List<ApartamentoListagemDto.MoradorDto> moradorDtos = apartamento.getMoradores().stream()
                .map(morador -> {
                    ApartamentoListagemDto.MoradorDto moradorDto = new ApartamentoListagemDto.MoradorDto();
                    moradorDto.setId(morador.getId());
                    moradorDto.setNome(morador.getNome());
                    return moradorDto;
                })
                .collect(Collectors.toList());
        dto.setMoradores(moradorDtos);

        // Conversão das entregas
        List<ApartamentoListagemDto.EntregaDto> entregaDtos = apartamento.getEntregas().stream()
                .map(entrega -> {
                    ApartamentoListagemDto.EntregaDto entregaDto = new ApartamentoListagemDto.EntregaDto();
                    entregaDto.setId(entrega.getId());
                    entregaDto.setTipoEntrega(entrega.getTipoEntrega());
                    entregaDto.setDataRecebimentoPorteiro(entrega.getDataRecebimentoPorteiro());
                    entregaDto.setDataRecebimentoMorador(entrega.getDataRecebimentoMorador());
                    entregaDto.setRecebido(entrega.getRecebido());
                    return entregaDto;
                })
                .collect(Collectors.toList());
        dto.setEntregas(entregaDtos);

        return dto;
    }
}
