package com.code.with.wasif.paymentgatewayspring.dto;

import lombok.Data;

/**
 * @author Wasif.Ali on 23/07/2024
 * @project payment-gateway-spring
 */
@Data
public class StripeTokenDTO {
    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String csv;
    private String token;
    private String username;
    private boolean success;
}
