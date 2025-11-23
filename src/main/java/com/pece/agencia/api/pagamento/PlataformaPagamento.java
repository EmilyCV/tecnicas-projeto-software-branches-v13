package com.pece.agencia.api.pagamento;

import com.stripe.exception.StripeException;

public interface PlataformaPagamento {
    String pagar(DadosCartao dadosCartao, double valor) throws StripeException;
}
