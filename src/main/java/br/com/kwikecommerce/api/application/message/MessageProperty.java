package br.com.kwikecommerce.api.application.message;

import br.com.kwikecommerce.api.application.SpringContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;


@AllArgsConstructor
@Getter
public class MessageProperty {

    private static final Object NULL_VALUE_REPLACEMENT = "not-informed";
    private static final MessageSource messageSource = SpringContext.getBean(MessageSource.class);

    private final String text;

    public static String use(String messageKey) {
        return MessageProperty.of(messageKey).getText();
    }

    public static String use(String messageKey, Object... fields) {
        return MessageProperty.of(messageKey, fields).getText();
    }

    public static MessageProperty of(String messageKey) {
        var message = getText(messageKey, null);

        return new MessageProperty(message);
    }

    public static MessageProperty of(String messageKey, Object... fields) {
        var message = getText(messageKey, fields);

        return new MessageProperty(message);
    }

    private static String getText(String messageKey, Object... fields) {
        assert messageSource != null : "MessageSource has not been autowired";

        var locale = LocaleContextHolder.getLocale();
        var actualFields = fields == null || fields.length == 0 ? null : replaceNullFields(fields);
        try {
            return messageSource.getMessage(messageKey, actualFields, locale);
        } catch (NoSuchMessageException ignored) {
            throw new NoSuchMessageException(messageKey, locale);
        }
    }

    private static Object[] replaceNullFields(Object[] fields) {
        Object[] result = new Object[fields.length];
        for (var i = 0; i < fields.length; i++) {
            result[i] = fields[i] == null ? NULL_VALUE_REPLACEMENT : fields[i];
        }
        return result;
    }

}
