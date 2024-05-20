package sptech.school.apizeporteiro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.service.porteiro.PorteiroService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/porteiros")
public class PorteiroController {

    private final PorteiroService porteiroService;

    @PostMapping
    public ResponseEntity<Void> cadastrarPorteiro(@RequestBody Porteiro porteiro) {
        porteiroService.criarPorteiro(porteiro);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<Porteiro>> listar() {
        List<Porteiro> porteiros = porteiroService.listar();
        return ResponseEntity.status(200).body(porteiros);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorteiro(@PathVariable int id) {
        porteiroService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}