package br.com.locacar.api.venda_locatech.dto;

public record VeiculoDTO(
        Long id,
        String marca,
        String modelo,
        Integer anoFabricacao,
        Integer anoModelo,
        String cor
) {
}
