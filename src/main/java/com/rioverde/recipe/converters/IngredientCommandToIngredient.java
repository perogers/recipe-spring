package com.rioverde.recipe.converters;

import com.rioverde.recipe.commands.IngredientCommand;
import com.rioverde.recipe.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient>{
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand command) {
        if (command == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(command.getId());
        ingredient.setAmount(command.getAmount());
        ingredient.setDescription(command.getDescription());
        ingredient.setUom(uomConverter.convert(command.getUom()));
        return ingredient;
    }
}
