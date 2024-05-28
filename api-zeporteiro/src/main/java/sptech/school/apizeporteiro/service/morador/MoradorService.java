package sptech.school.apizeporteiro.service.morador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.domain.morador.repository.MoradorRepository;
import sptech.school.apizeporteiro.mapper.ApartamentoMapper;
import sptech.school.apizeporteiro.mapper.CondominioMapper;
import sptech.school.apizeporteiro.mapper.MoradorMapper;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorCriacaoDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoradorService {
    private final MoradorRepository moradorRepository;
    private final ApartamentoRepository apartamentoRepository;

    public void cadastrarMoradores(List<MoradorCriacaoDto> moradorCriacaoDtos, Integer fkApartamento) {
        Apartamento apartamento = apartamentoRepository.findById(fkApartamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado"));

        List<Morador> moradores = MoradorMapper.toEntityList(moradorCriacaoDtos, apartamento);

        moradorRepository.saveAll(moradores);
    }

    public void cadastrarMorador(MoradorCriacaoDto moradorCriacaoDto, Integer fkApartamento) {
        Apartamento apartamento = apartamentoRepository.findById(fkApartamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado"));

        Morador morador = MoradorMapper.toEntity(moradorCriacaoDto, apartamento);

        moradorRepository.save(morador);
    }

    public List<MoradorListagemDto> listarPorApartamento(int apartamentoId) {
        List<Morador> moradores = moradorRepository.findByApartamentoId(apartamentoId);
        return moradores.stream()
                .map(MoradorMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateMorador(Integer id, MoradorCriacaoDto moradorCriacaoDto) {
        Morador moradorExistente = moradorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));

        moradorExistente.setNome(moradorCriacaoDto.getNome());
        moradorExistente.setNumeroWhats1(moradorCriacaoDto.getNumeroWhats1());
        moradorExistente.setNumeroWhats2(moradorCriacaoDto.getNumeroWhats2());
        moradorExistente.setNumeroWhats3(moradorCriacaoDto.getNumeroWhats3());

        Apartamento apartamento = apartamentoRepository.findById(moradorCriacaoDto.getFkApartamento())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado"));

        moradorExistente.setApartamento(apartamento);

        moradorRepository.save(moradorExistente);
    }

    public void deleteMorador(Integer id) {
        if (!moradorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado");
        }
        moradorRepository.deleteById(id);
    }
}


