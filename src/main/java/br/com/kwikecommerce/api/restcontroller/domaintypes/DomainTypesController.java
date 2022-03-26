package br.com.kwikecommerce.api.restcontroller.domaintypes;

import br.com.kwikecommerce.api.application.domaintype.DomainTypeMapper;
import br.com.kwikecommerce.api.restcontroller.domaintypes.dto.DomainTypeListingResponseDto;
import br.com.kwikecommerce.api.order.OrderStatusType;
import br.com.kwikecommerce.api.order.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/domain-types")
public class DomainTypesController implements DomainTypesApi {

    private final DomainTypeMapper domainTypeMapper;

    @Override
    public Set<DomainTypeListingResponseDto> findOrderStatusTypes() {
        return domainTypeMapper.map(OrderStatusType.values());
    }

    @Override
    public Set<DomainTypeListingResponseDto> findPaymentMethods() {
        return domainTypeMapper.map(PaymentMethod.values());
    }

}
