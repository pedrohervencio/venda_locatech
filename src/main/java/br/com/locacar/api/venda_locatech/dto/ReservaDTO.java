package br.com.locacar.api.venda_locatech.dto;

import br.com.locacar.api.venda_locatech.entities.Veiculo;

import java.time.LocalDate;

public record ReservaDTO(
        LocalDate dataRetirada,
        Integer qtdeDiarias,
        Integer vlrDiaria,
        Veiculo veiculo

) {
}
