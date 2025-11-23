package com.pece.agencia.api.veiculo;

import com.pece.agencia.api.hotelaria.Periodo;

import java.util.UUID;

public interface PlataformaLocacaoVeiculo {
    String locar(ReservaVeiculoRequest request);
    String obterCodigoPlataformaLocacaoVeiculo(UUID localidade);

    record ReservaVeiculoRequest(UUID codigoLocalidade, String categoria, Motorista motorista, Periodo periodo) {
    }
}
