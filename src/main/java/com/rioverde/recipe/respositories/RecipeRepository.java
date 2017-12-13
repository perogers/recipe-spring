package com.rioverde.recipe.respositories;

import com.rioverde.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

    public Optional<Recipe> findRecipeByDescriptionIsLike(String like);

    public Optional<Recipe> findRecipeById(Long id);

}
