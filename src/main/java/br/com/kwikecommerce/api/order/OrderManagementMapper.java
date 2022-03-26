package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.company.CompanyMapper;
import org.mapstruct.Mapper;


@Mapper(uses = CompanyMapper.class)
public interface OrderManagementMapper {


}
