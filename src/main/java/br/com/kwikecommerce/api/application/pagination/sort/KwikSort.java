package br.com.kwikecommerce.api.application.pagination.sort;

import br.com.kwikecommerce.api.application.exceptionhandling.InternalServerException;
import br.com.kwikecommerce.api.application.message.MessageProperty;
import lombok.Getter;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class KwikSort<T extends Enum> {

    private static final String SORT_PARTS_SEPARATOR = " ";
    private static final String SORTS_SEPARATOR = ";";

    @Getter
    private final Sort sort;

    public KwikSort(Class<T> anEnum, String aString) {
        var sortSections = splitSections(aString);
        var sortOption = KwikSortOptionFactory.create(anEnum);
        if (KwikSort.isNotASortString(sortOption, sortSections))
            throw new InternalServerException(
                MessageProperty.of("e.sort.wrongly-formatted-or-invalid-option", sortOption.toString()),
                MessageProperty.of("log.attempted-to-build-invalid-sort", anEnum.getName(), aString)
            );

        this.sort = this.createSort(sortOption, sortSections);
    }

    private static boolean isNotASortString(KwikSortOption kwikSortOption, Set<SortSection> sections) {
        for (var section : sections) {
            var isValidDirection = Sort.Direction.fromOptionalString(section.getDirection()).isPresent();
            var isValidPropertyOption = kwikSortOption.findPropertyNameForOption(section.getOption()).isPresent();

            if (isValidDirection && isValidPropertyOption)
                return false;
        }

        return true;
    }

    private <U extends KwikSortOption> Sort createSort(U sortOption, Set<SortSection> sections) {
        var orders = new ArrayList<Sort.Order>();
        sections.forEach(section -> {
            var direction = Sort.Direction.fromString(section.getDirection());
            var property = sortOption.mustFindPropertyNameForOption(section.getOption());

            orders.add(new Sort.Order(direction, property));
        });

        return Sort.by(orders);
    }

    private Set<SortSection> splitSections(String aString) {
        if (StringUtils.isBlank(aString))
            return Collections.emptySet();

        var result = new HashSet<SortSection>();
        var sorts = aString.split(SORTS_SEPARATOR);
        for (var sort : sorts) {
            if (!sort.contains(SORT_PARTS_SEPARATOR))
                return Collections.emptySet();

            var sortParts = sort.split(SORT_PARTS_SEPARATOR);
            var property = sortParts[0];
            var direction = sortParts[1];

            result.add(new SortSection(property, direction));
        }

        return result;
    }

    @Value
    private static final class SortSection {
        String option;
        String direction;
    }

}
