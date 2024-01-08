package br.com.locacar.api.venda_locatech.repository;

import br.com.locacar.api.venda_locatech.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
