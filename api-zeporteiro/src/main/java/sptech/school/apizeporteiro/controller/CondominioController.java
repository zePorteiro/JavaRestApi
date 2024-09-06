package sptech.school.apizeporteiro.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.service.condominio.CondominioService;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioListagemDto;

import java.util.List;

@RestController
@RequestMapping("/condominios")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CondominioController {
    private final CondominioService condominioService;

    @PostMapping
    public ResponseEntity<Condominio> criar(@RequestBody @Valid CondominioCriacaoDto condominioCriacaoDto) {
        Condominio novoCondominio = condominioService.criar(condominioCriacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCondominio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@RequestBody CondominioCriacaoDto condominioCriacaoDto, @PathVariable int id) {
        condominioService.atualizar(condominioCriacaoDto, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        condominioService.excluir(id);
        return ResponseEntity.status(HttpStatus.OK).build();
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