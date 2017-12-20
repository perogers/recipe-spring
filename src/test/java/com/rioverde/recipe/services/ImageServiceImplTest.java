package com.rioverde.recipe.services;

import com.rioverde.recipe.commands.RecipeCommand;
import com.rioverde.recipe.controllers.ImageController;
import com.rioverde.recipe.domain.Recipe;
import com.rioverde.recipe.respositories.RecipeRepository;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeService recipeService;

    ImageController controller;

    ImageService imageService;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
        controller = new ImageController(recipeService,imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testSaveImagefile() throws Exception {
        // Given
        Long recipeId = new Long(1L);
        MultipartFile multipartFile = new MockMultipartFile("imageFile",
                "testing.txt",
                "text/plain",
                "Hello World".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        // when
        imageService.saveImageFile(recipeId, multipartFile);

        // then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);

    }


}
