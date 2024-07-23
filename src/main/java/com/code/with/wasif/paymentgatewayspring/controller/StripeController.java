package com.code.with.wasif.paymentgatewayspring.controller;

import com.code.with.wasif.paymentgatewayspring.dto.StripeChargeDTO;
import com.code.with.wasif.paymentgatewayspring.dto.StripeTokenDTO;
import com.code.with.wasif.paymentgatewayspring.service.StripeService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wasif.Ali on 23/07/2024
 * @project payment-gateway-spring
 */
@RestController
@RequestMapping("/stripe")
public class StripeController {
    @Autowired
    StripeService service;

    @PostMapping("/card/token")
    public StripeTokenDTO createCardToken(@RequestBody StripeTokenDTO model) throws StripeException {
        return service.createCardToken(model);
    }
    @PostMapping("/card/charge")
    public StripeChargeDTO charge(@RequestBody StripeChargeDTO model){
        return service.charge(model);
    }
}
