package sptech.school.apizeporteiro.domain.entrega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.entrega.Entrega;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
    // SELECT * FROM entrega WHERE tipoEntrega LIKE 'XPTO'
    List<Entrega> findByTipoEntregaIgnoreCase(String tipoEntrega);

    // SELECT * FROM entrega WHERE dataRecebimentoPorteiro >= 'XPTO' AND dataRecebimentoPorteiro < 'XPTO'
    List<Entrega> findAllByDataRecebimentoPorteiroAfter(LocalDate dataRecebimentoPorteiro);

    // SELECT * FROM entrega WHERE dataRecebimentoMorador >= 'XPTO' AND dataRecebimentoMorador < 'XPTO'
    List<Entrega> findAllByDataRecebimentoMoradorAfter(LocalDate dataRecebimentoMorador);

    // SELECT * FROM entrega WHERE recebido = 'true'
    List<Entrega> findByRecebidoTrue();

    // SELECT * FROM entrega WHERE recebido = 'false'
    List<Entrega> findByRecebidoFalse();

    // SELECT COUNT(*) FROM entrega WHERE fk_apartamento = XPTO
//    Long countByFkApartamento(Integer apartamentoId);
}
