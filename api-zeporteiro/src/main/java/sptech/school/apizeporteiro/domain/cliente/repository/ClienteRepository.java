package sptech.school.apizeporteiro.domain.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.cliente.Cliente;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // SELECT * FROM cliente WHERE email LIKE '%XPTO%'
    List<Cliente> findByEmailIgnoreCase(String email);

    // SELECT * FROM cliente WHERE representante LIKE '%XPTO%'
    List<Cliente> findByRepresentanteIgnoreCase(String representante);

    // SELECT * FROM cliente WHERE cnpj LIKE '%XPTO%'
    List<Cliente> findByCnpjIgnoreCase(String cnpj);

    // Utilizado no JWT
    Optional<Cliente> findByEmail(String email);



}
