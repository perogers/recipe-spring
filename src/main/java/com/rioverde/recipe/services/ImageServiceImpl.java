package com.rioverde.recipe.services;

import com.rioverde.recipe.domain.Recipe;
import com.rioverde.recipe.respositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile imageFile) {
        log.debug("Got image file for recipe ID: " + recipeId);

        if( imageFile == null || imageFile.isEmpty() ) {
            log.error("Image file is null or empty");
            return;
        }

        Optional<Recipe> recipeOptional = recipeRepository.findById(Long.valueOf(recipeId));
        if(! recipeOptional.isPresent() ) {
            log.error("Recipe not found for ID: " + recipeId);
            return;
        }

        Recipe recipe = recipeOptional.get();

        try {
            Byte [] bytes = new Byte[imageFile.getBytes().length];
            byte [] imageBytes = imageFile.getBytes();
            Arrays.setAll(bytes, i -> imageBytes[i]);
            recipe.setImage(bytes);
            recipeRepository.save(recipe);
        }
        catch (IOException ioe) {
            log.error("Failed to save image: " + ioe.getMessage(), ioe);
        }

    }
}
