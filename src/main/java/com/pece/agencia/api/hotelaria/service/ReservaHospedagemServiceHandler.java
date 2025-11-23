package com.pece.agencia.api.hotelaria.service;

import com.pece.agencia.api.hotelaria.domain.OfertaHospedagemPlataformaMapping;

public interface ReservaHospedagemServiceHandler {
    boolean accepts(OfertaHospedagemPlataformaMapping mapping);
    String reservar(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request);
}
