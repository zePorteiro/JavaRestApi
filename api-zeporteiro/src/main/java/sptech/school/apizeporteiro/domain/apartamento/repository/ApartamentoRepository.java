package sptech.school.apizeporteiro.domain.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;

import java.util.List;

@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento, Integer> {
    // MOSTRA TODOS OS BLOCOS DE UM DETERMINADO CONDOMÍNIO
//    @Query("SELECT bloco FROM apartamento WHERE fk_cliente = :fkCliente GROUP BY bloco")
//    List<Apartamento> buscaBlocosCondominio(Integer fkCliente);

    // MOSTRA A CONTAGEM DE APARTAMENTOS TOTAIS DE UM DETERMINADO CONDOMÍNIO
//    @Query("SELECT COUNT(id) FROM apartamento WHERE fk_cliente = :fkCliente")
//    Long countApartamentoCondominio(Integer fkCliente);

    // SELECT * FROM apartamento WHERE num_ap like 'XPTO'

    List<Apartamento> findByCondominioId(Integer condominioId);


    List<Apartamento> findByNumApIgnoreCase(String numAp);

    // SELECT * FROM apartamento WHERE vazio = 'true'
    List<Apartamento> findByVazioTrue();

    // SELECT * FROM apartamento WHERE vazio = 'false'
    List<Apartamento> findByVazioFalse();
}
