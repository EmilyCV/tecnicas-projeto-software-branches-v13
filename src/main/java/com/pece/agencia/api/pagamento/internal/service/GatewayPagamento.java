package com.pece.agencia.api.pagamento.internal.service;


import com.pece.agencia.api.pagamento.PlataformaPagamento;
import com.pece.agencia.api.pagamento.DadosCartao;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.TokenCreateParams;
import org.springframework.stereotype.Service;

@Service
public class GatewayPagamento implements PlataformaPagamento {
    public String pagar(DadosCartao dadosCartao, double value) throws StripeException {
        TokenCreateParams.Card card = TokenCreateParams.Card.builder()
                .setNumber(dadosCartao.numero())
                .setExpMonth(String.valueOf(dadosCartao.dataExpiracao().getMonthValue()))
                .setExpYear(String.valueOf(dadosCartao.dataExpiracao().getYear()))
                .setCvc(dadosCartao.cvc())
                .build();

        Token token = Token.create(TokenCreateParams.builder().setCard(card).build());

        ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                .setDescription("Venda de Pacote")
                .setCurrency("brl")
                .setAmount((long) (value * 100)) // em centavos
                .setSource(token.getCard().getId())
                .build();
        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }
}
