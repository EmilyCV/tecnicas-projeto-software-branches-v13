package com.pece.agencia.api.core.service.acl.hotelaria;

import com.pece.agencia.api.core.service.acl.hotelaria.mapper.ReservaHospedagemRequestMapper;
import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.core.domain.OfertaHospedagem;
import com.pece.agencia.api.core.domain.Periodo;
import com.pece.agencia.api.hotelaria.service.ReservaHospedagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservaHospedagemServiceAdapter {

    private final ReservaHospedagemRequestMapper mapper;
    private final ReservaHospedagemService hospedagemService;

    public String reservar(OfertaHospedagem hospedagem, Cliente cliente, Periodo periodoViagem) {
        ReservaHospedagemService.ReservaHospedagemRequest request = mapper.toRequest(hospedagem, cliente, periodoViagem);
        return hospedagemService.reservar(request);
    }

    public String obterIdPlataforma(OfertaHospedagem hospedagem) {
        return hospedagemService.obterIdPlataforma(hospedagem.getId());
    }
}
