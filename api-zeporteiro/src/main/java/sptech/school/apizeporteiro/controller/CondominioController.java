package sptech.school.apizeporteiro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.mapper.CondominioMapper;
import sptech.school.apizeporteiro.service.condominio.CondominioService;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioListagemDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/condominios")
public class CondominioController {
    private final CondominioRepository condominioRepository;
    private final CondominioService condominioService;
    @PostMapping
    public ResponseEntity<CondominioListagemDto> cadastrarCondominio
            (@RequestBody @Valid CondominioCriacaoDto novoCondominio) {
        Condominio condominio = CondominioMapper.toEntity(novoCondominio);

        Condominio condominioSalvo = condominioRepository.save(condominio);

        CondominioListagemDto listagemCondominio = CondominioMapper.toDto(condominioSalvo);

        return ResponseEntity.status(201).body(listagemCondominio);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CondominioListagemDto> atualizarCondominio(
            @PathVariable Integer id,
            @Valid @RequestBody CondominioCriacaoDto condominio
    ) {
        CondominioListagemDto condominioAtualizado = condominioService.atualizarCondominio(id, condominio);
        return ResponseEntity.ok(condominioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCondominio(@PathVariable Integer id) {
        condominioService.excluirCondominio(id);
        return ResponseEntity.noContent().build();
    }
}
