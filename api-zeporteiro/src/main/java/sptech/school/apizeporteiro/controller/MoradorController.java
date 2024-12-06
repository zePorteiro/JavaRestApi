package sptech.school.apizeporteiro.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.mapper.MoradorMapper;
import sptech.school.apizeporteiro.service.morador.MoradorService;
import sptech.school.apizeporteiro.service.morador.autenticacao.dto.MoradorDetalhesDto;
import sptech.school.apizeporteiro.service.morador.autenticacao.dto.MoradorTokenDto;
import sptech.school.apizeporteiro.service.morador.dto.CadastroMoradorDto;
import sptech.school.apizeporteiro.service.morador.dto.LoginMoradorDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorResponseDto;

import java.util.List;

@RestController
@RequestMapping("/moradores")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MoradorController {
    private final MoradorService moradorService;

//    @PostMapping("/apartamento/{fkApartamento}")
//    public ResponseEntity<Void> cadastrarMorador(@RequestBody MoradorCriacaoDto moradorCriacaoDto, @PathVariable Integer fkApartamento) {
//        moradorService.cadastrarMorador(moradorCriacaoDto, fkApartamento);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping("/cadastrarMorador")
    public ResponseEntity<MoradorTokenDto> cadastrarMorador(@RequestBody @Valid CadastroMoradorDto dto) {
        MoradorTokenDto moradorTokenDto = moradorService.cadastrarMorador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(moradorTokenDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MoradorTokenDto> login(@RequestBody @Valid LoginMoradorDto loginDto) {
        try {
            MoradorTokenDto tokenDto = moradorService.authenticate(loginDto);
            return ResponseEntity.ok(tokenDto);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/apartamento/{apartamentoId}")
    public ResponseEntity<List<MoradorListagemDto>> listarPorApartamento(@PathVariable int apartamentoId) {
        List<MoradorListagemDto> moradores = moradorService.listarPorApartamento(apartamentoId);
        return ResponseEntity.ok(moradores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoradorResponseDto> getMoradorById(@PathVariable Integer id) {
        return moradorService.findById(id)
                .map(morador -> ResponseEntity.ok(MoradorMapper.toResponseDto(morador)))
                .orElse(ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Void> atualizarMorador(@PathVariable Integer id, @RequestBody MoradorCriacaoDto moradorCriacaoDto) {
//        moradorService.updateMorador(id, moradorCriacaoDto);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMorador(@PathVariable Integer id) {
        moradorService.deleteMorador(id);
        return ResponseEntity.noContent().build();
    }
}
