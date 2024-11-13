package sptech.school.apizeporteiro.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.domain.porteiro.repository.PorteiroRepository;
import sptech.school.apizeporteiro.service.porteiro.PorteiroService;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroCriacaoDto;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroListagemDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/porteiros")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PorteiroController {

    private final PorteiroService porteiroService;
    private final PorteiroRepository porteiroRepository;

    @PostMapping
    public ResponseEntity<PorteiroListagemDto> criar(@RequestBody PorteiroCriacaoDto porteiroCriacaoDto) {
        PorteiroListagemDto criado = porteiroService.criar(porteiroCriacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/condominio/{condominioId}")
    public ResponseEntity<List<PorteiroListagemDto>> listarPorCondominio(@PathVariable Integer condominioId) {
        List<Porteiro> porteiros = porteiroRepository.findByCondominioId(condominioId);

        if (porteiros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PorteiroListagemDto> porteiroDtos = porteiros.stream()
                .map(porteiro -> {
                    PorteiroListagemDto dto = new PorteiroListagemDto();
                    dto.setId(porteiro.getId());
                    dto.setNome(porteiro.getNome());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(porteiroDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PorteiroListagemDto> atualizar(@PathVariable Integer id, @RequestBody PorteiroCriacaoDto porteiroCriacaoDto) {
        PorteiroListagemDto atualizado = porteiroService.atualizar(id, porteiroCriacaoDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        porteiroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{porteiroId}/entregas")
    public ResponseEntity<List<PorteiroListagemDto.EntregaDto>> listarEntregasPorPorteiro(@PathVariable Integer porteiroId) {
        List<PorteiroListagemDto.EntregaDto> entregas = porteiroService.listarEntregasPorPorteiro(porteiroId);
        return ResponseEntity.ok(entregas);
    }
}
