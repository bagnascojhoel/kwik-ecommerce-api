package br.com.kwikecommerce.api.company;

import br.com.kwikecommerce.api.application.database.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class CompanyOwned extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Company company;

}
