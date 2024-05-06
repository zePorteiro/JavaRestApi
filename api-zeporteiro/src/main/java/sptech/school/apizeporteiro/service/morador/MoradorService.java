package sptech.school.apizeporteiro.service.morador;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.domain.morador.repository.MoradorRepository;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoradorService {
    private final MoradorRepository moradorRepository;

    public List<MoradorListagemDto> salvarApartamentos(List<MoradorListagemDto> moradoresDto) {
        List<Morador> moradores = new ArrayList<>();

        for (MoradorListagemDto moradorDTO : moradoresDto) {
            Morador morador = new Morador();
            morador.setNome(moradorDTO.getNome());
            morador.setNumeroWhats1(moradorDTO.getNumeroWhats1());
            morador.setNumeroWhats2(moradorDTO.getNumeroWhats2());
            morador.setNumeroWhats3(moradorDTO.getNumeroWhats3());
            moradorRepository.save(morador);
            moradores.add(morador);
        }
        return convertToDTOs(moradores);
    }

    private List<MoradorListagemDto> convertToDTOs(List<Morador> moradores) {
        List<MoradorListagemDto> dtos = new ArrayList<>();

        for (Morador morador : moradores) {
            MoradorListagemDto dto = new MoradorListagemDto();
            dto.setId(morador.getId());
            dto.setNome(morador.getNome());
            dto.setNumeroWhats1(morador.getNumeroWhats1());
            dto.setNumeroWhats2(morador.getNumeroWhats2());
            dto.setNumeroWhats3(morador.getNumeroWhats3());
            dtos.add(dto);
        }

        return dtos;
    }
}
