package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.application.exceptionhandling.NoSuchEnumOptionException;
import br.com.kwikecommerce.api.application.message.MessageProperty;
import br.com.kwikecommerce.api.application.logging.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@RequiredArgsConstructor
@Component
@Converter
public class OrderStatusTypeAttributeConverter implements AttributeConverter<OrderStatusType, String> {

    private final LogService logService;

    @Override
    public String convertToDatabaseColumn(OrderStatusType orderStatusType) {
        return orderStatusType.getDescription();
    }

    @Override
    public OrderStatusType convertToEntityAttribute(String aString) {
        try {
            return OrderStatusType.discoverByDescription(aString);
        } catch (NoSuchEnumOptionException noSuchEnumOptionException) {
            logService.logError(
                MessageProperty.use("log.database.could-not-convert-enum", noSuchEnumOptionException.getMessage())
            );
            return null;
        }
    }

}
