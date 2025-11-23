package com.pece.agencia.api.veiculo.internal.service;

import com.pece.agencia.api.veiculo.PlataformaLocacaoVeiculo;
import com.pece.agencia.api.veiculo.internal.domain.CodigoPlataformaLocacaoVeiculoMapping;
import com.pece.agencia.api.veiculo.internal.repository.CodigoPlataformaLocacaoVeiculoMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocacaoVeiculoService implements PlataformaLocacaoVeiculo {

    @Value("${plataforma.locadora-veiculos.url}")
    private String plataformaLocadoraVeculosBaseUrl;

    private final CodigoPlataformaLocacaoVeiculoMappingRepository repository;

    public String locar(ReservaVeiculoRequest request) {
        CodigoPlataformaLocacaoVeiculoMapping mapping = repository.findById(request.codigoLocalidade()).get();

        Map<String, String> payload = new HashMap<>();
        payload.put("motorista", request.motorista().nome());
        payload.put("categoria", request.categoria());
        payload.put("codigo-cidade", mapping.getCodigoPlataformaLocacaoVeiculo());
        payload.put("data-check-in", request.periodo().inicio().toString());
        payload.put("data-check-out", request.periodo().fim().toString());

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaLocadoraVeculosBaseUrl + "/api/v1/veiculos/reservas", payload, Map.class);

        return result.get("numero-reserva");
    }

    public String obterCodigoPlataformaLocacaoVeiculo(UUID localidade) {
        CodigoPlataformaLocacaoVeiculoMapping mapping = repository.findById(localidade).get();
        return mapping.getCodigoPlataformaLocacaoVeiculo();
    }
}
