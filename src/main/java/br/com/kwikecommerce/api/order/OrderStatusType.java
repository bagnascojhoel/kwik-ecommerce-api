package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.application.exceptionhandling.NoSuchEnumOptionException;
import br.com.kwikecommerce.api.application.domaintype.DomainType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum OrderStatusType implements DomainType {
    PENDING("PENDENTE"),
    VIEWED("VISUALIZADO"), // TODO jhoel.bagnasco 24/08/2021 | Remover esse status
    ON_GOING("EM ANDAMENTO"),
    CANCELED("CANCELADO"),
    SENT("ENVIADO"),
    DELIVERED("ENTREGUE");

    private final String description;

    public static OrderStatusType discoverByDescription(String description) throws NoSuchEnumOptionException {
        for (var orderStatusType : values()) {
            if (orderStatusType.getDescription().equalsIgnoreCase(description))
                return orderStatusType;
        }

        throw new NoSuchEnumOptionException(description);
    }

}
