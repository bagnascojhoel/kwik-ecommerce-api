package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.application.database.AbstractEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Immutable
@Table(name = "pedido_status")
public class OrderStatus extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Order order;

    @Convert(converter = OrderStatusTypeAttributeConverter.class)
    @Column(name = "status")
    private OrderStatusType type;

    @Size(max = 50)
    @Column(name = "motivo")
    private String reason;

}
