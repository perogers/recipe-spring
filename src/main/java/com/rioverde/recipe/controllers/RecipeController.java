package com.rioverde.recipe.controllers;


import com.rioverde.recipe.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Data
@AllArgsConstructor
@Controller
public class RecipeController {


    private RecipeService recipeService;



    @RequestMapping({"/recipe/show/{id}"})
    public String showById(@PathVariable String id, Model model){
        log.debug("Entering for id: " + id);
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }



}
