package sptech.school.apizeporteiro.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
public class MoradorController {
    private final MoradorService moradorService;

    @PostMapping("/apartamento/{fkApartamento}")
    public ResponseEntity<Void> cadastrarMorador(@RequestBody MoradorCriacaoDto moradorCriacaoDto, @PathVariable Integer fkApartamento) {
        moradorService.cadastrarMorador(moradorCriacaoDto, fkApartamento);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/lista/apartamento/{fkApartamento}")
    public ResponseEntity<Void> cadastrarMoradores(@RequestBody List<MoradorCriacaoDto> moradorCriacaoDtos, @PathVariable Integer fkApartamento) {
        moradorService.cadastrarMoradores(moradorCriacaoDtos, fkApartamento);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/apartamento/{apartamentoId}")
    public ResponseEntity<List<MoradorListagemDto>> listarPorApartamento(@PathVariable int apartamentoId) {
        List<MoradorListagemDto> moradores = moradorService.listarPorApartamento(apartamentoId);
        return ResponseEntity.ok(moradores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarMorador(@PathVariable Integer id, @RequestBody MoradorCriacaoDto moradorCriacaoDto) {
        moradorService.updateMorador(id, moradorCriacaoDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMorador(@PathVariable Integer id) {
        moradorService.deleteMorador(id);
        return ResponseEntity.noContent().build();
    }
}
