package com.rioverde.recipe.controllers;

import com.rioverde.recipe.domain.Category;
import com.rioverde.recipe.domain.UnitOfMeasure;
import com.rioverde.recipe.respositories.CategoryRepository;
import com.rioverde.recipe.respositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    CategoryRepository categoryRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @RequestMapping({"", "/", "index"})
    public String getIndexPage() {
        Optional<Category> optionalCategory = categoryRepository.findByDescription("Japanese");
        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findUnitOfMeasureByDescription("Teaspoon");

        System.out.println("Category: " + optionalCategory.get().toString());
        System.out.println("Unit of Measure: " + optionalUnitOfMeasure.get().toString());

        return "index";
    }
}
