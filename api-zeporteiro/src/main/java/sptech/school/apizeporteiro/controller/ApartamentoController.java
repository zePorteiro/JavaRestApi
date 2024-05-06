package sptech.school.apizeporteiro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.apizeporteiro.service.apartamento.ApartamentoService;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoListagemDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apartamentos")
public class ApartamentoController {
    private final ApartamentoService apartamentoService;

    @PostMapping
    public ResponseEntity<List<ApartamentoListagemDto>> salvarApartamentos(@RequestBody List<ApartamentoListagemDto> apartamentosDTO) {
        List<ApartamentoListagemDto> apartamentosListagemDTO = apartamentoService.salvarApartamentos(apartamentosDTO);
        return ResponseEntity.status(201).body(apartamentosListagemDTO);
    }
}
