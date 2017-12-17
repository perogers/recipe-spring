package com.rioverde.recipe.converters;

import com.rioverde.recipe.commands.IngredientCommand;
import com.rioverde.recipe.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient == null) {
            return null;
        }

        IngredientCommand command = new IngredientCommand();
        command.setId(ingredient.getId());
        command.setAmount(ingredient.getAmount());
        command.setDescription(ingredient.getDescription());
        command.setUom( uomConverter.convert(ingredient.getUom()));

        return command;
    }
}
