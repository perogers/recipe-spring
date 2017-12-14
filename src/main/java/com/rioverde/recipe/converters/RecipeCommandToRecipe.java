package com.rioverde.recipe.converters;

import com.rioverde.recipe.commands.RecipeCommand;
import com.rioverde.recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe>{

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 NotesCommandToNotes notesCommandToNotes) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand command) {
        if(command == null) {
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setId(command.getId());
        recipe.setCookTime(command.getCookTime());
        recipe.setNotes(notesCommandToNotes.convert(command.getNotes()));
        recipe.setPrepTime(command.getPrepTime());
        recipe.setServings(command.getServings());
        recipe.setDifficulty(command.getDifficulty());
        recipe.setDirections(command.getDirections());
        recipe.setSource(command.getSource());
        recipe.setUrl(command.getUrl());
        recipe.setImage(command.getImage());
        recipe.setDescription(command.getDescription());

        if(command.getCategories() != null && command.getCategories().size() > 0) {
            command.getCategories()
                    .forEach(categoryCommand -> recipe.addCategory(categoryCommandToCategory.convert(categoryCommand)));
        }

        if(command.getIngredients() != null && command.getIngredients().size() > 0) {
            command.getIngredients()
                    .forEach(ingredientCommand -> recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand)));
        }

        return recipe;

    }
}
