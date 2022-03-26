package br.com.kwikecommerce.api.company;

import br.com.kwikecommerce.api.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Long> {

}
