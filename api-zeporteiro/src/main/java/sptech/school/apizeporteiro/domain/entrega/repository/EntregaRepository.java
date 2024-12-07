package sptech.school.apizeporteiro.domain.entrega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.entrega.Entrega;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
    // SELECT * FROM entrega WHERE tipoEntrega LIKE 'XPTO'
//    List<Entrega> findByTipoEntregaIgnoreCase(String tipoEntrega);

    // SELECT * FROM entrega WHERE dataRecebimentoPorteiro >= 'XPTO' AND dataRecebimentoPorteiro < 'XPTO'
    List<Entrega> findAllByDataRecebimentoPorteiroAfter(LocalDate dataRecebimentoPorteiro);

    List<Entrega> findByCondominioId(Integer condominioId);

    // SELECT * FROM entrega WHERE dataRecebimentoMorador >= 'XPTO' AND dataRecebimentoMorador < 'XPTO'
    List<Entrega> findAllByDataRecebimentoMoradorAfter(LocalDate dataRecebimentoMorador);

    List<Entrega> findByRecebidoFalse();
    long countByDataRecebimentoMoradorAfter(LocalDate date);

    Optional<Entrega> findTopByOrderByDataRecebimentoMoradorDesc();

    List<Entrega> findByApartamentoNumAp(String numeroApartamento);


}
