package com.pece.agencia.api.hotelaria;

import java.util.UUID;

public interface PlataformaHotelaria {
    String reservar(ReservaHospedagemRequest request);
    String obterIdPlataforma(UUID oferta);
}
