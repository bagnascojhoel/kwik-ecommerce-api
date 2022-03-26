package br.com.kwikecommerce.api.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Long create(Category category) {
        return categoryRepository.save(category).getId();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll().stream()
            .collect(Collectors.toList());
    }

}
