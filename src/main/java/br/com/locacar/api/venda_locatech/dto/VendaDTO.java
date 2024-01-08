package br.com.locacar.api.venda_locatech.dto;

import br.com.locacar.api.venda_locatech.entities.Administrativo;
import br.com.locacar.api.venda_locatech.entities.Diretor;
import br.com.locacar.api.venda_locatech.entities.Veiculo;
import br.com.locacar.api.venda_locatech.valueobject.Status;

import java.time.LocalDate;

public record VendaDTO(
        Long numero,
        LocalDate dataAgendada,
        Diretor diretor,
        Administrativo administrativo,
        Veiculo veiculo,
        Status status
) {
}
