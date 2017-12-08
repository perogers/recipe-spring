package com.rioverde.recipe.bootstrap;

import com.rioverde.recipe.domain.*;
import com.rioverde.recipe.respositories.CategoryRepository;
import com.rioverde.recipe.respositories.RecipeRepository;
import com.rioverde.recipe.respositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
public class DevBootstrap  implements ApplicationListener<ContextRefreshedEvent>{

    CategoryRepository categoryRepository;
    RecipeRepository recipeRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;

    public DevBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }


    private void initData() {
        log.debug("Starting dev data load");

        // Get & verify units of measure
        Optional<UnitOfMeasure> eachUoM = unitOfMeasureRepository.findUnitOfMeasureByDescription("Each");
        if(!eachUoM.isPresent()) {
            throw new RuntimeException("Each Unit of measure not present");
        }
        Optional<UnitOfMeasure> teaspoonUoM = unitOfMeasureRepository.findUnitOfMeasureByDescription("Teaspoon");
        if(!teaspoonUoM.isPresent()) {
            throw new RuntimeException("Teaspoon Unit of measure not present");
        }
        Optional<UnitOfMeasure> tablespoonUoM = unitOfMeasureRepository.findUnitOfMeasureByDescription("Tablespoon");
        if(!tablespoonUoM.isPresent()) {
            throw new RuntimeException("Tablespoon Unit of measure not present");
        }
        Optional<UnitOfMeasure> pinchUoM = unitOfMeasureRepository.findUnitOfMeasureByDescription("Pinch");
        if(!pinchUoM.isPresent()) {
            throw new RuntimeException("Pinch Unit of measure not present");
        }
        Optional<UnitOfMeasure> ounceUoM = unitOfMeasureRepository.findUnitOfMeasureByDescription("Ounce");
        if(!ounceUoM.isPresent()) {
            throw new RuntimeException("Ounce Unit of measure not present");
        }
        Optional<UnitOfMeasure> dashUoM = unitOfMeasureRepository.findUnitOfMeasureByDescription("Dash");
        if(!dashUoM.isPresent()) {
            throw new RuntimeException("Dash Unit of measure not present");
        }

        // Get & verify categories
        Optional<Category> mexicanCat = categoryRepository.findByDescription("Mexican");
        if(!mexicanCat.isPresent()) {
            throw new RuntimeException("Mexican category not present");
        }
        Optional<Category> americanCat = categoryRepository.findByDescription("American");
        if(!americanCat.isPresent()) {
            throw new RuntimeException("American category not present");
        }
        Optional<Category> italianCat = categoryRepository.findByDescription("Italian");
        if(!italianCat.isPresent()) {
            throw new RuntimeException("Italian category not present");
        }
        Optional<Category> japaneseCat = categoryRepository.findByDescription("Japanese");
        if(!japaneseCat.isPresent()) {
            throw new RuntimeException("Japanese category not present");
        }
        Optional<Category> fastFoodCat = categoryRepository.findByDescription("Fast Food");
        if(!fastFoodCat.isPresent()) {
            throw new RuntimeException("Fast Food category not present");
        }

        log.debug("Creating dip recipe");
        // Dip recipe
        Recipe dip = new Recipe();
        dip.getCategories().add(mexicanCat.get());
        dip.setCookTime(0);
        dip.setPrepTime(10);
        dip.setDifficulty(Difficulty.EASY);
        dip.setServings(4);
        dip.setSource("Simply Recipes");
        dip.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        dip.setDescription("The BEST guacamole");
        dip.setDifficulty(Difficulty.EASY);
        dip.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the " +
                "avocado with a blunt knife and scoop out the flesh with a spoon.Place in bowel\n" +
                "  2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime " +
                "juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. " +
                "So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with " +
                "this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and " +
                "to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) " +
                "Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");

        dip.addIngredient( new Ingredient("Ripe avacados", new BigDecimal(2), eachUoM.get()));
        dip.addIngredient( new Ingredient("Kosher salt", new BigDecimal(0.5), teaspoonUoM.get()));
        dip.addIngredient( new Ingredient("Cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tablespoonUoM.get()));
        dip.addIngredient( new Ingredient("Minced red onion", new BigDecimal(2), tablespoonUoM.get()));
        dip.addIngredient( new Ingredient("Serrano chiles, stems and seeds removed, minced", new BigDecimal(1), eachUoM.get()));

        dip.setNotes(new Notes("Garnish with red radishes or jicama. Serve with tortilla chips."));

        recipeRepository.save(dip);

        log.debug("Creating taco recipe");
        // Chicken taco recipe
        Recipe tacos = new Recipe();
        tacos.getCategories().add(mexicanCat.get());
        tacos.getCategories().add(americanCat.get());
        tacos.setDifficulty(Difficulty.MODERATE);
        tacos.setServings(5);
        tacos.setPrepTime(10);
        tacos.setCookTime(15);
        tacos.setDescription("Spicy grilled chicken tacos");
        tacos.setSource("Simply Recipes");
        tacos.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, " +
                "oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make " +
                "a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted " +
                "into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. " +
                "As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat " +
                "for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of " +
                "arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with " +
                "the thinned sour cream. Serve with lime wedges.");

        tacos.addIngredient(new Ingredient("Ancho chili powder", new BigDecimal(2), tablespoonUoM.get()));
        tacos.addIngredient(new Ingredient("Dried oregano", new BigDecimal(1), teaspoonUoM.get()));
        tacos.addIngredient(new Ingredient("Dried cumin", new BigDecimal(1),teaspoonUoM.get()));
        tacos.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUoM.get()));
        tacos.addIngredient(new Ingredient("Salt", new BigDecimal(0.5), teaspoonUoM.get()));
        tacos.addIngredient(new Ingredient("Clove garlic", new BigDecimal(1), eachUoM.get()));

        tacos.setNotes(new Notes("Look for ancho chile powder with the Mexican ingredients at your grocery " +
                "store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the " +
                "oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)"));

        recipeRepository.save(tacos);
    }
}
