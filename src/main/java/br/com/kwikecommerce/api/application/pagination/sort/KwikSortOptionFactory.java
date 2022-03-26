package br.com.kwikecommerce.api.application.pagination.sort;

import br.com.kwikecommerce.api.application.exceptionhandling.InternalServerException;
import br.com.kwikecommerce.api.application.message.MessageProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KwikSortOptionFactory {

    private static final Map<Class<? extends Enum>, KwikSortOption> createdSortOptions = new HashMap<>();

    static KwikSortOption create(Class<? extends Enum> anEnum) {
        if (!createdSortOptions.containsKey(anEnum)) {
            var isSortOption = Arrays.stream(anEnum.getInterfaces())
                .anyMatch(anInterface -> anInterface.equals(SortOption.class));
            if (!isSortOption)
                throw new InternalServerException(MessageProperty.of("log.kwik-sort-option-must-have-property-name-getter"));

            createdSortOptions.put(anEnum, new KwikSortOption(anEnum));
        }

        return createdSortOptions.get(anEnum);
    }

}
