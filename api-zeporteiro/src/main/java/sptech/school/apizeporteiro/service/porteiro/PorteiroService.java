package sptech.school.apizeporteiro.service.porteiro;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.domain.porteiro.repository.PorteiroRepository;
import sptech.school.apizeporteiro.mapper.CondominioMapper;
import sptech.school.apizeporteiro.mapper.PorteiroMapper;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroCriacaoDto;

@Service
@RequiredArgsConstructor
public class PorteiroService {

    private final PorteiroRepository porteiroRepository;

    public void criar(PorteiroCriacaoDto porteiroCriacaoDto) {
//        final Porteiro novoPorteiro = PorteiroMapper.toEntity(porteiroCriacaoDto);
//        this.porteiroRepository.save(novoPorteiro);
    }
}
