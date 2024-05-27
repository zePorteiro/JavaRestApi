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

        List<Morador> moradores = MoradorMapper.toEntityList(moradorCriacaoDtos).stream()
                .peek(morador -> morador.setApartamento(apartamento))
                .collect(Collectors.toList());

        moradorRepository.saveAll(moradores);
    }

    public void cadastrarMorador(MoradorCriacaoDto moradorCriacaoDto, Integer fkApartamento) {
        Apartamento apartamento = apartamentoRepository.findById(fkApartamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado"));

        Morador morador = MoradorMapper.toEntity(moradorCriacaoDto);
        morador.setApartamento(apartamento);

        moradorRepository.save(morador);
    }

    public List<MoradorListagemDto> listarPorCondominio(int condominioId) {
        List<Morador> moradores = moradorRepository.findByFkCondominio(condominioId);
        return moradores.stream()
                .map(MoradorMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MoradorListagemDto> listarPorApartamento(int apartamentoId) {
        List<Morador> moradores = moradorRepository.findByFkApartamento(apartamentoId);
        return moradores.stream()
                .map(MoradorMapper::toDto)
                .collect(Collectors.toList());
    }
}
