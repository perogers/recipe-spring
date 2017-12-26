package com.rioverde.recipe.controllers;


import com.rioverde.recipe.commands.RecipeCommand;
import com.rioverde.recipe.exceptions.NotFoundException;
import com.rioverde.recipe.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Data
@AllArgsConstructor
@Controller
public class RecipeController {

    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    private RecipeService recipeService;

    @RequestMapping({"/recipe/{id}/show"})
    public String showById(@PathVariable String id, Model model){
        log.debug("Entering showById for id: " + id);
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }


    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id,Model model) {
        log.debug("Entering updateRecipe");
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        log.debug("Entering newRecipe");
        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPEFORM_URL;
    }


    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe")  RecipeCommand command, BindingResult bindingResult) {
        log.debug("Entering saveOrUpdate");

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.error(objectError.toString()));

            return RECIPE_RECIPEFORM_URL;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Entering deleteRecipe for ID: " + id);
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception: " + exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }



}
