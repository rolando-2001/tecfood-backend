package com.backend.app.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PaypalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public PayPalHttpClient getPaypalClient(){
        if (mode.equals("live")) return new PayPalHttpClient(new PayPalEnvironment.Live(clientId, clientSecret));
        return new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
    }


}
