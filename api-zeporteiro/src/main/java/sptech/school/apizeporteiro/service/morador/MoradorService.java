package sptech.school.apizeporteiro.service.morador;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.confiiguration.security.jwt.GerenciadorTokenJwt;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.domain.morador.repository.MoradorRepository;
import sptech.school.apizeporteiro.mapper.MoradorMapper;
import sptech.school.apizeporteiro.service.morador.autenticacao.dto.MoradorTokenDto;
import sptech.school.apizeporteiro.service.morador.dto.CadastroMoradorDto;
import sptech.school.apizeporteiro.service.morador.dto.LoginMoradorDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoradorService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final MoradorRepository moradorRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private final ApartamentoRepository apartamentoRepository;

    @Autowired
    private CondominioRepository condominioRepository;

    public MoradorTokenDto authenticate(LoginMoradorDto loginDto) {
        try {
            // Primeiro verifica se o morador existe
            Morador morador = moradorRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos"));

            // Cria o token de autenticação
            UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getSenha());

            // Tenta autenticar
            Authentication authentication = this.authenticationManager.authenticate(credentials);

            // Se chegou aqui, a autenticação foi bem sucedida
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Gera o token JWT
            String token = gerenciadorTokenJwt.generateToken(authentication);

            // Retorna o DTO com o token
            return MoradorMapper.of(morador, token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }
    }

    public MoradorTokenDto cadastrarMorador(CadastroMoradorDto dto) {
        // Verifica se o email já está cadastrado
        if (moradorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        // Verifica se o condomínio existe
        Condominio condominio = condominioRepository.findByCep(dto.getCep());
        if (condominio == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado para o CEP fornecido");
        }

        // Busca o apartamento
        Apartamento apartamento = apartamentoRepository
                .findByNumApAndCondominioId(dto.getNumeroApartamento(), condominio.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Apartamento não encontrado neste condomínio"));

        // Cria o morador
        Morador morador = MoradorMapper.toEntity(dto, condominio, apartamento);

        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(morador.getSenha());
        morador.setSenha(senhaCriptografada);

        // Salva o morador
        Morador moradorSalvo = moradorRepository.save(morador);

        // Autenticar o novo morador para gerar o token
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                dto.getEmail(), dto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return MoradorMapper.of(moradorSalvo, token);
    }

    public Optional<Morador> findById(Integer id) {
        return moradorRepository.findById(id);
    }

    public List<MoradorListagemDto> listarPorApartamento(int apartamentoId) {
        List<Morador> moradores = moradorRepository.findByApartamentoId(apartamentoId);
        return moradores.stream()
                .map(MoradorMapper::toDto)
                .collect(Collectors.toList());
    }

//    public void updateMorador(Integer id, MoradorCriacaoDto moradorCriacaoDto) {
//        Morador moradorExistente = moradorRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));
//
//        moradorExistente.setNome(moradorCriacaoDto.getNome());
//        moradorExistente.setNumeroWhats1(moradorCriacaoDto.getNumeroWhats1());
//        moradorExistente.setNumeroWhats2(moradorCriacaoDto.getNumeroWhats2());
//        moradorExistente.setNumeroWhats3(moradorCriacaoDto.getNumeroWhats3());
//
//        Apartamento apartamento = apartamentoRepository.findById(moradorCriacaoDto.getFkApartamento())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado"));
//
//        moradorExistente.setApartamento(apartamento);
//
//        moradorRepository.save(moradorExistente);
//    }

    public void deleteMorador(Integer id) {
        if (!moradorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado");
        }
        moradorRepository.deleteById(id);
    }
}