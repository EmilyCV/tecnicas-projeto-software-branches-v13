package com.pece.agencia.api.aereo.internal.service;

import com.pece.agencia.api.aereo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReservaTransladoAereoService implements PlataformaTransladoAereo {

    @Value("${plataforma.empresa-aerea.url}")
    private String plataformaEmpresaAereaBaseUrl;

    public ReservaVoo reservar(ReservaTransladoAereoRequest request) {
        Map<String, String> resultadoReserva = this.doReservar(request.passageiro().nome(), request.dadosVoo().numero(), request.data());

        ReservaVoo reservaVoo = new ReservaVoo(
                resultadoReserva.get("eticket"),
                resultadoReserva.get("assento"),
                request.data().atTime(request.dadosVoo().horario()),
                request.dadosVoo()
        );

        return reservaVoo;
    }

    private Map<String, String> doReservar(String passageiro, String numeroVoo, LocalDate dataIda) {
        Map<String, String> request = new HashMap<>();
        request.put("passageiro", passageiro);
        request.put("data", dataIda.toString());

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaEmpresaAereaBaseUrl + "/api/v1/voos/" + numeroVoo + "/reservas", request, Map.class);

        return result;
    }
}
