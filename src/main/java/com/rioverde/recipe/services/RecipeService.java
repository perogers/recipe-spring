package com.rioverde.recipe.services;

import com.rioverde.recipe.commands.RecipeCommand;
import com.rioverde.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    public Set<Recipe> getRecipes();
    public Recipe findById(Long id);
    public RecipeCommand saveRecipeCommand(RecipeCommand command);
    public RecipeCommand findCommandById(Long id);
    public void deleteById(Long id);
}
