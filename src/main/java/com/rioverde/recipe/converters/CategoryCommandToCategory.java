package com.rioverde.recipe.converters;

import com.rioverde.recipe.commands.CategoryCommand;
import com.rioverde.recipe.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{


    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand command) {
        if(command == null) {
            return null;
        }

        Category category = new Category();
        category.setId(command.getId());
        category.setDescription(command.getDescription());

        return category;
    }
}
