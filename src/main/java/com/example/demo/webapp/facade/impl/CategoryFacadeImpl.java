package com.example.demo.webapp.facade.impl;


import com.example.demo.entities.entity.category.Category;
import com.example.demo.service.entity.category.CategoryService;
import com.example.demo.webapp.facade.CategoryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryFacadeImpl implements CategoryFacade {

    private final CategoryService categoryService;

    @Override
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }
}
