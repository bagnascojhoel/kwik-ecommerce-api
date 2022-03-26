package br.com.kwikecommerce.api.company;

import br.com.kwikecommerce.api.company.Company;
import org.mapstruct.Mapper;


@Mapper
public interface CompanyMapper {

    default Company map(Long companyId) {
        return Company.builder()
            .id(companyId)
            .build();
    }

}
