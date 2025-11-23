package com.pece.agencia.api.hotelaria;

import java.util.UUID;

public record ReservaHospedagemRequest(UUID codigoPromocao, Hospede hospede, Periodo periodo) {
}
