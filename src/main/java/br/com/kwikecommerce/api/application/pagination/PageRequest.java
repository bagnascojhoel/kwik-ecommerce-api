package br.com.kwikecommerce.api.application.pagination;

import br.com.kwikecommerce.api.application.pagination.sort.KwikSort;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PageRequest<T extends Enum> implements Pageable {

    @Schema(example = "3", minimum = "0")
    @NotNull
    @Min(0)
    private Integer page = 0;

    @Schema(
        description = "<prioritary field name> <ASC|DESC>; <secondary field name> <ASC|DESC>",
        example = "name DESC; price ASC"
    )
    private KwikSort<T> sort;

    @Schema(description = "Maximum number of itens per page", example = "5", minimum = "1")
    @Max(99)
    @Min(1)
    private Integer limit = 20;

    @Override
    public int getPageNumber() {
        return this.page;
    }

    @Override
    public int getPageSize() {
        return this.limit;
    }

    @Override
    public long getOffset() {
        return this.page * this.limit;
    }

    @Override
    public Sort getSort() {
        return this.sort.getSort();
    }

    @Override
    public Pageable next() {
        return new PageRequest<T>(this.page, this.sort, this.limit);
    }

    @Override
    public Pageable previousOrFirst() {
        return new PageRequest<T>(this.page == 0 ? 0 : this.page - 1, this.sort, this.limit);
    }

    @Override
    public Pageable first() {
        return new PageRequest<T>(0, this.sort, this.limit);
    }

    @Override
    public Pageable withPage(int i) {
        return new PageRequest<T>(i, this.sort, this.limit);
    }

    @Override
    public boolean hasPrevious() {
        return this.page > 0;
    }

}
