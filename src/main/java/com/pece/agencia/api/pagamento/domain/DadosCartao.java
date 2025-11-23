package com.pece.agencia.api.pagamento.domain;

import java.time.YearMonth;

public record DadosCartao(String numero, String cvc, YearMonth dataExpiracao) {
}
