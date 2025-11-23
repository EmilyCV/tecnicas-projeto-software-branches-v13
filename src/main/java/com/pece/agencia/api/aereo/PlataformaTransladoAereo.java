package com.pece.agencia.api.aereo;

import com.pece.agencia.api.aereo.internal.service.ReservaTransladoAereoService;

import java.time.LocalDate;

public interface PlataformaTransladoAereo {
    ReservaVoo reservar(ReservaTransladoAereoRequest request);
    record ReservaTransladoAereoRequest(Passageiro passageiro, DadosVoo dadosVoo, LocalDate data) {
    }

}
