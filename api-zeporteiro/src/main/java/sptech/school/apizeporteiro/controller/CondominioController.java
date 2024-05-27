package sptech.school.apizeporteiro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.mapper.CondominioMapper;
import sptech.school.apizeporteiro.service.condominio.CondominioService;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioListagemDto;

import java.util.List;

@RestController
@RequestMapping("/condominios")
@RequiredArgsConstructor
public class CondominioController {
    private final CondominioService condominioService;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody CondominioCriacaoDto condominioCriacaoDto) {
        condominioService.criar(condominioCriacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@RequestBody CondominioCriacaoDto condominioCriacaoDto, @PathVariable int id) {
        condominioService.atualizar(condominioCriacaoDto, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        condominioService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondominioListagemDto> listarUmPorId(@PathVariable int id) {
        CondominioListagemDto condominio = condominioService.listarUmPorId(id);
        return ResponseEntity.ok(condominio);
    }

    @GetMapping
    public ResponseEntity<List<CondominioListagemDto>> listarTodos() {
        List<CondominioListagemDto> condominios = condominioService.listarTodos();
        return ResponseEntity.ok(condominios);
    }
}

