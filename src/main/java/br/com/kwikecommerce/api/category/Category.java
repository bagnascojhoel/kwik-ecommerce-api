package br.com.kwikecommerce.api.category;

import br.com.kwikecommerce.api.company.CompanyOwned;
import br.com.kwikecommerce.api.product.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;


@SuperBuilder
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "categoria")
public class Category extends CompanyOwned {

    @Size(max = 30)
    @Column(name = "titulo")
    private String title;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

}
