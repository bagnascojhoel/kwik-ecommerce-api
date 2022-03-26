package br.com.kwikecommerce.api.application.pagination.sort;

import br.com.kwikecommerce.api.application.exceptionhandling.InternalServerException;
import br.com.kwikecommerce.api.application.message.MessageProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;


@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class KwikSortOption {

    private final Class<? extends Enum> sortOptionEnum;

    public String mustFindPropertyNameForOption(String sortOption) {
        return this.findPropertyNameForOption(sortOption)
            .orElseThrow(() -> new InternalServerException(
                MessageProperty.of("log.attempted-to-build-invalid-sort", sortOption)
            ));
    }

    public Optional<String> findPropertyNameForOption(String sortOption) {
        return Arrays.stream(sortOptionEnum.getEnumConstants())
            .filter(anEnum -> anEnum.name().equalsIgnoreCase(sortOption))
            .map(anEnum -> (SortOption) anEnum)
            .map(SortOption::getPropertyName)
            .findFirst();
    }

    @Override
    public String toString() {
        var enumConstants = sortOptionEnum.getEnumConstants();
        if (ArrayUtils.isEmpty(enumConstants))
            return StringUtils.EMPTY;

        var resultBuilder = new StringBuilder(enumConstants[0].name());
        for (var i = 1; i < enumConstants.length; i++) {
            resultBuilder.append(", ").append(enumConstants[i]);
        }

        return resultBuilder.toString();
    }
}
