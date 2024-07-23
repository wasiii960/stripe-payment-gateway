package com.code.with.wasif.paymentgatewayspring.service;

import com.code.with.wasif.paymentgatewayspring.dto.StripeChargeDTO;
import com.code.with.wasif.paymentgatewayspring.dto.StripeTokenDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wasif.Ali on 23/07/2024
 * @project payment-gateway-spring
 */
@Service
@Slf4j
public class StripeService {
    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = apiKey;
    }

    public StripeTokenDTO createCardToken(StripeTokenDTO dto) throws StripeException {
        try{
            Map<String,Object> card = new HashMap<>();
            card.put("number",dto.getCardNumber());
            card.put("exp_month",Integer.parseInt(dto.getExpMonth()));
            card.put("exp_year",Integer.parseInt(dto.getExpYear()));
            card.put("cvc",dto.getCsv());
            Map<String,Object> params = new HashMap<>();
            params.put("card",card);
            Token token = Token.create(params);
            if(token!=null && token.getId()!=null){
                dto.setSuccess(true);
                dto.setToken(token.getId());
            }
            return dto;
        }catch (StripeException ex){
            log.error("Stripe Service :::: createCardToken :::: {} ",ex);
            throw new RuntimeException(ex.getMessage());
        }
    }
    public StripeChargeDTO charge(StripeChargeDTO chargeRequest){
        try{
            chargeRequest.setSuccess(false);
            Map<String,Object> chargeParams = new HashMap<>();
            chargeParams.put("amount",(int)(chargeRequest.getAmount()*100));
            chargeParams.put("currency","USD");
            chargeParams.put("description","Payment for id "+chargeRequest.getAdditionalInfo().getOrDefault("ID_TAG",""));
            Map<String,Object> metaData= new HashMap<>();
            metaData.putAll(chargeRequest.getAdditionalInfo());
            chargeParams.put("metadata",metaData);
            Charge charge = Charge.create(chargeParams);
            chargeRequest.setMessage(charge.getOutcome().getSellerMessage());
            if(charge.getPaid()){
                chargeRequest.setChargeId(charge.getId());
                chargeRequest.setSuccess(true);
            }
            return chargeRequest;
        }catch (StripeException ex){
            log.error("StripeService ::::: charge :::: {} ",ex);
            throw new RuntimeException(ex.getMessage());
        }
    }
}
