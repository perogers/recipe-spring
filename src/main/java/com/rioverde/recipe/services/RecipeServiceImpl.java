package com.rioverde.recipe.services;

import com.rioverde.recipe.commands.RecipeCommand;
import com.rioverde.recipe.converters.RecipeCommandToRecipe;
import com.rioverde.recipe.converters.RecipeToRecipeCommand;
import com.rioverde.recipe.domain.Recipe;
import com.rioverde.recipe.respositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("In getRecipes");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        log.debug("In findById for ID: " + id);
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (!optionalRecipe.isPresent()) {
            throw new RuntimeException("Recipe for ID: " + id + " not found");
        }

        return optionalRecipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        log.debug("In findCommandById for ID: " + id);
        return recipeToRecipeCommand.convert(findById(id));
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        log.debug("In deleteById for ID: " + id);
        recipeRepository.deleteById(id);
    }
}
