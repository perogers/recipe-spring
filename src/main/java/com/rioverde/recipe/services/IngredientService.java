package com.rioverde.recipe.services;

import com.rioverde.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    public IngredientCommand saveIngredientCommand(IngredientCommand command);
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
