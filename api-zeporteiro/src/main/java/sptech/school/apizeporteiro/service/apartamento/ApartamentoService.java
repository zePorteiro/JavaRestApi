package sptech.school.apizeporteiro.service.apartamento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.mapper.ApartamentoMapper;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoCriacaoDto;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoListagemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartamentoService {

    private final ApartamentoRepository apartamentoRepository;

    public List<ApartamentoListagemDto> salvarApartamentos(List<ApartamentoCriacaoDto> apartamentosDTO) {
        List<Apartamento> apartamentos = apartamentosDTO.stream()
                .map(ApartamentoMapper::toEntity)
                .collect(Collectors.toList());

        List<Apartamento> apartamentosSalvos = apartamentoRepository.saveAll(apartamentos);
        return ApartamentoMapper.toDtoList(apartamentosSalvos);
    }

    public ApartamentoListagemDto salvarApartamento(ApartamentoCriacaoDto apartamentoDTO) {
        Apartamento apartamento = ApartamentoMapper.toEntity(apartamentoDTO);
        Apartamento apartamentoSalvo = apartamentoRepository.save(apartamento);
        return ApartamentoMapper.toDto(apartamentoSalvo);
    }

    public ApartamentoListagemDto atualizarVazio(Integer id, boolean vazio) {
        Optional<Apartamento> optionalApartamento = apartamentoRepository.findById(id);
        if (optionalApartamento.isPresent()) {
            Apartamento apartamento = optionalApartamento.get();
            apartamento.setVazio(vazio);
            Apartamento apartamentoAtualizado = apartamentoRepository.save(apartamento);
            return ApartamentoMapper.toDto(apartamentoAtualizado);
        } else {
            throw new RuntimeException("Apartamento não encontrado"); // Ajuste isso conforme suas exceções
        }
    }

    public void excluirApartamento(Integer id) {
        if (apartamentoRepository.existsById(id)) {
            apartamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Apartamento não encontrado"); // Ajuste isso conforme suas exceções
        }
    }

    public List<ApartamentoListagemDto.MoradorDto> listarMoradoresPorApartamento(Integer id) {
        Optional<Apartamento> optionalApartamento = apartamentoRepository.findById(id);
        if (optionalApartamento.isPresent()) {
            Apartamento apartamento = optionalApartamento.get();
            return apartamento.getMoradores().stream()
                    .map(ApartamentoMapper::toDtoMorador)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Apartamento não encontrado");
        }
    }

    public List<ApartamentoListagemDto> listarTodosApartamentos() {
        List<Apartamento> apartamentos = apartamentoRepository.findAll();
        return ApartamentoMapper.toDtoList(apartamentos);
    }
}
