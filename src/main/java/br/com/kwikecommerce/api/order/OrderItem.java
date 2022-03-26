package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.application.database.AbstractEntity;
import br.com.kwikecommerce.api.product.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Immutable
@Table(name = "pedido_item")
public class OrderItem extends AbstractEntity {

    // Como transformar isso aqui em um Objeto de Valor?

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Order order;

    @Max(999999999)
    @Column(name = "quantidade")
    private Integer quantity;

    @DecimalMax("9999999.99")
    @Column(name = "preco_unitario_venda")
    private BigDecimal unitarySalePrice;

    @Size(max = 100)
    @Column(name = "descricao")
    private String description;

}
