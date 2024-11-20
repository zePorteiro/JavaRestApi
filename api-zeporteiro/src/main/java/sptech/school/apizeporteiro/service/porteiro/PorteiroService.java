package sptech.school.apizeporteiro.service.porteiro;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.entrega.repository.EntregaRepository;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.domain.porteiro.repository.PorteiroRepository;
import sptech.school.apizeporteiro.mapper.PorteiroMapper;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroCriacaoDto;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroListagemDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PorteiroService {

    private final PorteiroRepository porteiroRepository;
    private final CondominioRepository condominioRepository;
    private final EntregaRepository entregaRepository;

    public PorteiroListagemDto criar(PorteiroCriacaoDto porteiroCriacaoDto) {
        Condominio condominio = condominioRepository.findById(porteiroCriacaoDto.getCondominioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado"));

        Porteiro porteiro = PorteiroMapper.toEntity(porteiroCriacaoDto);
        porteiro.setCondominio(condominio);

        porteiroRepository.save(porteiro);

        return PorteiroMapper.toDto(porteiro);
    }

    public List<PorteiroListagemDto> listarPorCondominio(Integer condominioId) {
        List<Porteiro> porteiros = porteiroRepository.findByCondominioId(condominioId);

        // Chama a lógica de matriz e imprime a matriz de porteiros e entregas
        matrizPorteirosEntregas(porteiros);

        return porteiros.stream()
                .map(PorteiroMapper::toDto)
                .collect(Collectors.toList());
    }

    public PorteiroListagemDto atualizar(Integer id, PorteiroCriacaoDto porteiroCriacaoDto, PorteiroListagemDto porteiroListagemDto) {
        Porteiro porteiro = porteiroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Porteiro não encontrado"));

        Condominio condominio = condominioRepository.findById(porteiroCriacaoDto.getCondominioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado"));

        porteiro.setNome(porteiroCriacaoDto.getNome());
        porteiro.setRg(porteiroCriacaoDto.getRg());
        porteiro.setSenha(porteiroCriacaoDto.getSenha());
        porteiro.setCondominio(condominio);
        porteiro.setId(porteiroListagemDto.getId());

        porteiroRepository.save(porteiro);

        return PorteiroMapper.toDto(porteiro);
    }

    public void deletar(Integer id) {
        if (!porteiroRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Porteiro não encontrado");
        }
        porteiroRepository.deleteById(id);
    }

    public List<PorteiroListagemDto.EntregaDto> listarEntregasPorPorteiro(Integer porteiroId) {
        Porteiro porteiro = porteiroRepository.findById(porteiroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Porteiro não encontrado"));

        return PorteiroMapper.toEntregaDto(porteiro.getEntregas());
    }

    // Adicionando a lógica de matriz para porteiros e suas entregas
    public void matrizPorteirosEntregas(List<Porteiro> porteiros) {
        int numPorteiros = porteiros.size();

        int numTiposEntregas = 3;
        int[][] matriz = new int[numPorteiros][numTiposEntregas];

        for (int i = 0; i < numPorteiros; i++) {
            Porteiro porteiro = porteiros.get(i);
            List<Entrega> entregas = porteiro.getEntregas();
            int tipo1 = 0, tipo2 = 0, tipo3 = 0;
            for (Entrega entrega : entregas) {
                if (entrega.getTipoEntrega().equals("Tipo1")) tipo1++;
                if (entrega.getTipoEntrega().equals("Tipo2")) tipo2++;
                if (entrega.getTipoEntrega().equals("Tipo3")) tipo3++;
            }
            matriz[i][0] = tipo1;
            matriz[i][1] = tipo2;
            matriz[i][2] = tipo3;
        }

        // Exibindo a matriz
        exibirMatrizPorteirosEntregas(matriz, porteiros);
    }

    private void exibirMatrizPorteirosEntregas(int[][] matriz, List<Porteiro> porteiros) {
        System.out.println("Matriz de Porteiros e suas Entregas:");
        System.out.println("Porteiro | Tipo1 | Tipo2 | Tipo3");
        for (int i = 0; i < matriz.length; i++) {
            System.out.print(porteiros.get(i).getNome() + " | ");
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " | ");
            }
            System.out.println();
        }
    }
}


