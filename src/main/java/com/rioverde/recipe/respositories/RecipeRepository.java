package com.rioverde.recipe.respositories;

import com.rioverde.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

}
