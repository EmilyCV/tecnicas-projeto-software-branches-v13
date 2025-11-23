package com.pece.agencia.api.hotelaria.internal.service;

import com.pece.agencia.api.hotelaria.PlataformaHotelaria;
import com.pece.agencia.api.hotelaria.internal.domain.OfertaHospedagemPlataformaMapping;
import com.pece.agencia.api.hotelaria.Plataforma;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Component
public class PlataformaRegularReservaHospedagemServiceHandler implements ReservaHospedagemServiceHandler {
    @Value("${plataforma.hotel.regular.url}")
    private String plataformaHotelRegularBaseUrl;

    @Override
    public boolean accepts(OfertaHospedagemPlataformaMapping mapping) {
        return mapping.getPlataforma() == Plataforma.REGULAR;
    }

    @Override
    public String reservar(OfertaHospedagemPlataformaMapping mapping, PlataformaHotelaria.ReservaHospedagemRequest request) {
        Map<String, String> requestPayload = new HashMap<>();

        requestPayload.put("hospede", request.hospede().nome());
        requestPayload.put("data-check-in", request.periodo().inicio().toString());
        requestPayload.put("data-check-out", request.periodo().fim().toString());

        String idPlataforma = mapping.getCodigoHotel();

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaHotelRegularBaseUrl + "/api/v1/hoteis/" + idPlataforma + "/reservas", requestPayload, Map.class);
        return result.get("numero-reserva");
    }
}
