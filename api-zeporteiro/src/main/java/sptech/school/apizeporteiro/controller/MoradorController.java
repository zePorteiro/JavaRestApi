package sptech.school.apizeporteiro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.service.morador.MoradorService;
import sptech.school.apizeporteiro.service.morador.dto.MoradorCriacaoDto;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;

import java.util.List;

@RestController
@RequestMapping("/moradores")
@RequiredArgsConstructor
public class MoradorController {
    private final MoradorService moradorService;

    @GetMapping("/apartamento/{fkApartamento}")
    public ResponseEntity<List<MoradorListagemDto>> listarPorApartamento(@PathVariable int fkApartamento) {
        List<MoradorListagemDto> moradores = moradorService.listarPorApartamento(fkApartamento);
        return ResponseEntity.ok(moradores);
    }

    @PostMapping("/apartamento/{fkApartamento}")
    public ResponseEntity<Void> cadastrarMoradores(@PathVariable Integer fkApartamento, @RequestBody List<MoradorCriacaoDto> moradorCriacaoDtos) {
        moradorService.cadastrarMoradores(moradorCriacaoDtos, fkApartamento);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/apartamento/{fkApartamento}/single")
    public ResponseEntity<Void> cadastrarMorador(@PathVariable Integer fkApartamento, @RequestBody MoradorCriacaoDto moradorCriacaoDto) {
        moradorService.cadastrarMorador(moradorCriacaoDto, fkApartamento);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}