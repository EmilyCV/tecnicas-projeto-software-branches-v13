package com.pece.agencia.api.core.service.acl.pagamento;

import com.pece.agencia.api.core.domain.DadosCartao;
import com.pece.agencia.api.core.service.acl.pagamento.mapper.DadosCartaoMapper;
import com.pece.agencia.api.pagamento.service.GatewayPagamento;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayPagamentoAdapter {
    private final GatewayPagamento service;
    private final DadosCartaoMapper mapper;

    public String pagar(DadosCartao dadosCartao, double value) throws StripeException {
        return service.pagar(mapper.toDadosCartao(dadosCartao), value);
    }
}
