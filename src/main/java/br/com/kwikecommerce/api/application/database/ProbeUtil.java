package br.com.kwikecommerce.api.application.database;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProbeUtil {

    public static <T, B> B builderFor(Class<T> entityType) {
        try {
            Method entityBuilderMethod = entityType.getMethod("builder");
            var builderInstance = entityBuilderMethod.invoke(null);
            Class<B> entityBuilderClass = (Class<B>) entityType.getDeclaringClass();
            Method[] builderInstanceMethods = entityBuilderClass.getMethods();

            for (Method builderInstanceMethod : builderInstanceMethods) {
                if (builderInstanceMethod.getName() != "build")
                    builderInstanceMethod.invoke(builderInstance, null);
            }

            return entityBuilderClass.cast(builderInstance);
        } catch (Exception ignore) {
            throw new RuntimeException("problem on entity broke probe builder");
        }
    }

}
