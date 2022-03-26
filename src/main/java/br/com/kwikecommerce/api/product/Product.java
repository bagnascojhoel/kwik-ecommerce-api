package br.com.kwikecommerce.api.product;

import br.com.kwikecommerce.api.category.Category;
import br.com.kwikecommerce.api.company.CompanyOwned;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;


@SuperBuilder
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Table(name = "produto")
public final class Product extends CompanyOwned {

    @Size(min = 3, max = 100)
    @Column(name = "titulo")
    private String title;

    @Min(0)
    @Max(99999)
    @Column(name = "preco_unitario", precision = 2)
    private BigDecimal unitaryPrice;

    @Min(0)
    @Max(65535)
    @Column(name = "qtd_disponivel")
    private Integer availableQty;

    @Size(min = 3)
    @Column(name = "detalhes")
    private String details;

    @ManyToMany
    @JoinTable(
        name = "categoria_produto",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Category> categories;

    @ElementCollection
    @CollectionTable(name = "produto_imagem", joinColumns = @JoinColumn(name = "produto_id"))
    @Column(name = "url")
    private List<String> imagesUrls;

}
