package com.rioverde.recipe.controllers;


import com.rioverde.recipe.commands.RecipeCommand;
import com.rioverde.recipe.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Data
@AllArgsConstructor
@Controller
public class RecipeController {

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

        return "recipe/recipeform";
    }


    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        log.debug("Entering saveOrUpdate");
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Entering deleteRecipe for ID: " + id);
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

}
