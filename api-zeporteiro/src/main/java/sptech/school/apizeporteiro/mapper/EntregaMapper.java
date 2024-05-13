package sptech.school.apizeporteiro.mapper;

import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

public class EntregaMapper {
    public static Entrega toEntity(EntregaCriacaoDto dto) {
        if (dto == null) {
            return null;
        }

        Entrega entrega = new Entrega();

        entrega.setDataRecebimentoPorteiro(dto.getDataRecebimentoPorteiro());
        entrega.setDataRecebimentoMorador(dto.getDataRecebimentoMorador()); // vai ser nulo?
        // vão ter as fk?

        return entrega;
    }

    public static EntregaListagemDto toDto(Entrega entity) {
        if (entity == null) {
            return null;
        }

        EntregaListagemDto entregaListagemDto = new EntregaListagemDto();

        entregaListagemDto.setDataRecebimentoPorteiro(entity.getDataRecebimentoPorteiro());
        entregaListagemDto.setDataRecebimentoMorador(entity.getDataRecebimentoMorador());
//        entregaListagemDto.setRecebido(entity.isRecebido());

        return entregaListagemDto;
    }
}
