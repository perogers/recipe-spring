package com.rioverde.recipe.services;

import com.rioverde.recipe.commands.IngredientCommand;
import com.rioverde.recipe.converters.IngredientToIngredientCommand;
import com.rioverde.recipe.domain.Recipe;
import com.rioverde.recipe.respositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeService;


    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeService) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeService = recipeService;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if(!recipeOptional.isPresent()) {
            log.error("Recipe not found for ID: " + recipeId);
        }
        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient not found for ID: " + ingredientId);
        }

        return ingredientCommandOptional.get();

    }
}
