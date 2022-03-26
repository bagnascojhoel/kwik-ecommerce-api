package br.com.kwikecommerce.api.company;

import br.com.kwikecommerce.api.category.Category;
import br.com.kwikecommerce.api.application.database.AbstractEntity;
import br.com.kwikecommerce.api.order.Order;
import br.com.kwikecommerce.api.product.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;


@SuperBuilder
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "empresa")
public class Company extends AbstractEntity {

    @Size(max = 50)
    @Column(name = "nome")
    private String name;

    @Size(max = 300)
    @Column(name = "endereco")
    private String address;

    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @Size(max = 9)
    @Column(name = "telefone_whatsapp")
    private String whatsappPhone;

    @Column(name = "esta_ativo")
    private Boolean isActive;

    @OneToMany(mappedBy = "company")
    private List<Category> categories;

    @OneToMany(mappedBy = "company")
    private List<Product> products;

    @OneToMany(mappedBy = "company")
    private List<Order> orders;

}
