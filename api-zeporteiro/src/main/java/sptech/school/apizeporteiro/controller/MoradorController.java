package sptech.school.apizeporteiro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.apizeporteiro.service.morador.MoradorService;
import sptech.school.apizeporteiro.service.morador.dto.MoradorListagemDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/moradores")
public class MoradorController {
    private final MoradorService moradorService;

    @PostMapping
    public ResponseEntity<List<MoradorListagemDto>> salvarApartamentos(@RequestBody List<MoradorListagemDto> moradoresDTO) {
        List<MoradorListagemDto> moradorListagemDtos = moradorService.salvarApartamentos(moradoresDTO);
        return ResponseEntity.status(201).body(moradorListagemDtos);
    }
}
