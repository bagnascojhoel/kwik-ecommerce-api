package br.com.kwikecommerce.api.application.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @EqualsAndHashCode.Exclude
    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Exclude
    @Column(name = "alterado_em", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

}
