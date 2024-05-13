package sptech.school.apizeporteiro.service.condominio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.mapper.CondominioMapper;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioListagemDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.util.NoSuchElementException;

import static sptech.school.apizeporteiro.mapper.CondominioMapper.toDto;

@Service
@RequiredArgsConstructor
public class CondominioService {
    private final CondominioRepository condominioRepository;
    public void criar(CondominioCriacaoDto condominioCriacaoDto) {
        final Condominio novoCondominio = CondominioMapper.toEntity(condominioCriacaoDto);
        this.condominioRepository.save(novoCondominio);
    }

    public CondominioListagemDto atualizarCondominio(Integer id, CondominioCriacaoDto condominioDto) {
        Condominio condominio = condominioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Condominio não encontrado."));

        return toDto(condominioRepository.save(condominio));
    }
    public void excluirCondominio(Integer id) {
        condominioRepository.deleteById(id);
    }
}
