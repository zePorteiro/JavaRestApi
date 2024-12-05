package sptech.school.apizeporteiro.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.service.apartamento.ApartamentoService;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoCriacaoDto;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoListagemDto;

import java.util.List;

@RestController
@RequestMapping("/apartamentos")
@SecurityRequirement(name = "bearerAuth")
public class ApartamentoController {

    @Autowired
    private ApartamentoService apartamentoService;

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> verificarApartamento(
            @RequestParam String cep,
            @RequestParam String numero) {
        boolean exists = apartamentoService.verificarApartamentoExiste(cep, numero);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/salvar-lote")
    public ResponseEntity<List<ApartamentoListagemDto>> salvarApartamentos(@RequestBody List<ApartamentoCriacaoDto> apartamentosDTO) {
        List<ApartamentoListagemDto> apartamentosSalvos = apartamentoService.salvarApartamentos(apartamentosDTO);
        return ResponseEntity.ok(apartamentosSalvos);
    }

    @PostMapping("/{condominioId}")
    public ResponseEntity<Void> cadastrarApartamento(@RequestBody ApartamentoCriacaoDto apartamentoCriacaoDto,
                                                     @PathVariable Integer condominioId) {
        apartamentoService.cadastrarApartamento(apartamentoCriacaoDto, condominioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}/atualizar-vazio/{vazio}")
    public ResponseEntity<ApartamentoListagemDto> atualizarVazio(@PathVariable Integer id,
                                                                 @PathVariable boolean vazio) {
        ApartamentoListagemDto apartamentoAtualizado = apartamentoService.atualizarVazio(id, vazio);
        return ResponseEntity.ok(apartamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirApartamento(@PathVariable Integer id) {
        apartamentoService.excluirApartamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/moradores")
    public ResponseEntity<List<ApartamentoListagemDto.MoradorDto>> listarMoradoresPorApartamento(@PathVariable Integer id) {
        List<ApartamentoListagemDto.MoradorDto> moradores = apartamentoService.listarMoradoresPorApartamento(id);
        return ResponseEntity.ok(moradores);
    }

    @GetMapping
    public ResponseEntity<List<ApartamentoListagemDto>> listarTodosApartamentos() {
        List<ApartamentoListagemDto> apartamentos = apartamentoService.listarTodosApartamentos();
        return ResponseEntity.ok(apartamentos);
    }

    @GetMapping("/condominio/{condominioId}")
    public ResponseEntity<List<ApartamentoListagemDto>> listarApartamentosPorCondominio(@PathVariable Integer condominioId) {
        List<ApartamentoListagemDto> apartamentos = apartamentoService.listarApartamentosPorCondominio(condominioId);
        return ResponseEntity.ok(apartamentos);
    }
}
