package sptech.school.apizeporteiro.service.apartamento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoListagemDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartamentoService {
    private final ApartamentoRepository apartamentoRepository;

    public List<ApartamentoListagemDto> salvarApartamentos(List<ApartamentoListagemDto> apartamentosDTO) {
        List<Apartamento> apartamentos = new ArrayList<>();

        for (ApartamentoListagemDto apartamentoDTO : apartamentosDTO) {
            Apartamento apartamento = new Apartamento();
            apartamento.setBloco(apartamentoDTO.getBloco());
            apartamento.setVazio(apartamentoDTO.getVazio());
            apartamento.setNumAp(apartamentoDTO.getNumAp());
            apartamentoRepository.save(apartamento);
            apartamentos.add(apartamento);
        }
        return convertToDTOs(apartamentos);
    }

    private List<ApartamentoListagemDto> convertToDTOs(List<Apartamento> apartamentos) {
        List<ApartamentoListagemDto> dtos = new ArrayList<>();

        for (Apartamento apartamento : apartamentos) {
            ApartamentoListagemDto dto = new ApartamentoListagemDto();
            dto.setId(apartamento.getId());
            dto.setBloco(apartamento.getBloco());
            dto.setNumAp(apartamento.getNumAp());
            dto.setVazio(apartamento.isVazio());
            dtos.add(dto);
        }

        return dtos;
    }
}
