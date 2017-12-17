package com.rioverde.recipe.services;

import com.rioverde.recipe.converters.RecipeCommandToRecipe;
import com.rioverde.recipe.converters.RecipeToRecipeCommand;
import com.rioverde.recipe.domain.Recipe;
import com.rioverde.recipe.respositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;



public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Long id = new Long(1L);
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(id)).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(id);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() throws Exception {

        Recipe recipe = new Recipe();
        HashSet receipesData = new HashSet();
        receipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(receipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }


    @Test
    public void getRecipeById() {
        Long id = 10L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(id)).thenReturn( Optional.of(recipe) );

        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        assertTrue(recipeOptional.isPresent());
        assertEquals(id, recipeOptional.get().getId());
        verify(recipeRepository, times(1)).findById(id);

    }

    @Test
    public void testDeleteById() {
        Long id = new Long(10L);

        recipeService.deleteById(id);

        verify(recipeRepository, times(1)).deleteById(anyLong() );

    }
}