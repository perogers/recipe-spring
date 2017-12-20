package com.rioverde.recipe.controllers;

import com.rioverde.recipe.commands.RecipeCommand;
import com.rioverde.recipe.domain.Recipe;
import com.rioverde.recipe.services.ImageService;
import com.rioverde.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@Controller
public class ImageController {

    private final RecipeService recipeService;

    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }


    @GetMapping("/recipe/{recipeId}/image")
    public String getImageFormForRecipe(@PathVariable String recipeId, Model model) {
        log.debug("getImageFormForRecipe for recipe ID: " + recipeId);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        if(recipeCommand == null) {
            log.error("Failed to find recipe for ID: " + recipeId);
            return "/";
        }
        model.addAttribute("recipe", recipeCommand);

        return "/recipe/ingredient/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String addImageForRecipe(@PathVariable String recipeId, @RequestParam("imagefile")MultipartFile imageFile) {
        imageService.saveImageFile(Long.valueOf(recipeId), imageFile);

        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void getImageForRecipe(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        log.debug("Entering for ID: " + recipeId);

        RecipeCommand recipeCommand = recipeService.findCommandById((Long.valueOf(recipeId)));
        if( recipeCommand == null || recipeCommand.getImage() == null) {
            log.error("No recipe or recipe image for ID: " + recipeId);
            return;
        }
        byte [] bytes = new byte[recipeCommand.getImage().length];
        Byte [] imageBytes = recipeCommand.getImage();
        int i = 0;
        for(Byte boxedByte : recipeCommand.getImage()) {
            bytes[i++] = boxedByte;
        }


        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(bytes);
        IOUtils.copy(is, response.getOutputStream());
    }

}
