package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.application.domaintype.DomainType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
// TODO pegar tradução utilizando MessageProperty
public enum PaymentMethod implements DomainType {
    PIX("PIX"),
    CREDIT_CARD("CARTÃO DE CRÉDITO"),
    DEBIT_CARD("CARTÃO DE DÉBITO"),
    OTHER("OUTRO");

    @Getter
    private final String description;

}
