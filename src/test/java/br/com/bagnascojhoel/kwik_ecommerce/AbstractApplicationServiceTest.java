package br.com.bagnascojhoel.kwik_ecommerce;

import jakarta.validation.Valid;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = {ApplicationServiceConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
public abstract class AbstractApplicationServiceTest {

    private static String currentTransactionName;

    protected static void setupTransactionName(final String transactionName) {
        TransactionSynchronizationManager.setCurrentTransactionName(transactionName);
        currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
    }

    protected static void assertTransactionIsPropagated() {
        Assert.notNull(currentTransactionName, "You need to call setupTransactionName before assertion");

        assertThat(TransactionSynchronizationManager.getCurrentTransactionName()).isEqualTo(currentTransactionName);
    }

    protected static void assertMethodHasValidParam(
            final Class<?> aClass,
            final String aMethod,
            final String aParameter
    ) throws NoSuchMethodException, NoSuchFieldException {
        final Class<?> userClass = ClassUtils.getUserClass(aClass);
        final Method method = Arrays.stream(userClass.getDeclaredMethods()).sequential()
                .filter(param -> param.getName().equalsIgnoreCase(aMethod))
                .findAny()
                .orElseThrow(() -> new NoSuchMethodException("method does not exist"));

        final Parameter parameter = Arrays.stream(method.getParameters()).sequential()
                .filter(param -> param.getName().equalsIgnoreCase(aParameter))
                .findAny()
                .orElseThrow(() -> new NoSuchFieldException("parameter does not exist"));

        assertThat(parameter.isAnnotationPresent(Valid.class)).isTrue();
    }

}
