package sptech.school.apizeporteiro.service.morador;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.domain.morador.repository.MoradorRepository;
import sptech.school.apizeporteiro.mapper.ApartamentoMapper;
import sptech.school.apizeporteiro.mapper.CondominioMapper;
import sptech.school.apizeporteiro.mapper.MoradorMapper;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.morador.autenticacao.MoradorTokenDto;
import sptech.school.apizeporteiro.service.morador.dto.CadastroMoradorDto;
import sptech.school.apizeporteiro.service.morador.dto.LoginMoradorDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorCriacaoDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoradorService {
    private final MoradorRepository moradorRepository;

    @Autowired
    private final ApartamentoRepository apartamentoRepository;

    @Autowired
    private CondominioRepository condominioRepository;

    public MoradorTokenDto authenticate(LoginMoradorDto loginDto) throws AuthenticationException {
        Morador morador = moradorRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos"));

        if (!morador.getSenha().equals(loginDto.getSenha())) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        // Criar e retornar o DTO sem o token
        MoradorTokenDto moradorTokenDto = new MoradorTokenDto();
        moradorTokenDto.setUserId(morador.getId());
        moradorTokenDto.setNome(morador.getNome());
        moradorTokenDto.setEmail(morador.getEmail());

        return moradorTokenDto;
    }

    public void cadastrarMoradores(List<MoradorCriacaoDto> moradorCriacaoDtos, Integer fkApartamento) {
        Apartamento apartamento = apartamentoRepository.findById(fkApartamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado"));

        List<Morador> moradores = MoradorMapper.toEntityList(moradorCriacaoDtos, apartamento);

        moradorRepository.saveAll(moradores);
    }

    public Morador cadastrarMorador(CadastroMoradorDto dto) {
        Condominio condominio = condominioRepository.findByCep(dto.getCep());
        if (condominio == null) {
            throw new IllegalArgumentException("Condomínio não encontrado para o CEP fornecido.");
        }

        Morador morador = MoradorMapper.toEntity(dto, condominio);
        return moradorRepository.save(morador);
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