package br.com.kwikecommerce.api.category;

import java.util.List;


public interface CategoryService {

    Long create(Category category);

    List<Category> findAll();

}
