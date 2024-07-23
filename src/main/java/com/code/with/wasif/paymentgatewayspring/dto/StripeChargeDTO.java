package com.code.with.wasif.paymentgatewayspring.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wasif.Ali on 23/07/2024
 * @project payment-gateway-spring
 */
@Data
public class StripeChargeDTO {
    private String stripeToken;
    private String username;
    private Double amount;
    private Boolean success;
    private String message;
    private String chargeId;
    private Map<String,Object> additionalInfo = new HashMap<>();
}
