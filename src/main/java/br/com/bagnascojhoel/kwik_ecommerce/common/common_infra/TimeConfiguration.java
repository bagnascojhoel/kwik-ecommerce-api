package br.com.bagnascojhoel.kwik_ecommerce.common.common_infra;

import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeConfiguration {
    private static final String BRAZIL_TIME_ZONE = "America/Sao_Paulo";

    public TimeConfiguration() {
        TimeZone.setDefault(TimeZone.getTimeZone(BRAZIL_TIME_ZONE));
    }
}
