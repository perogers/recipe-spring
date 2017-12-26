package com.rioverde.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"categories"})
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    private String source;

    @URL
    private String url;

    @NotBlank
    @Lob
    private String directions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name="recipe_category",
                joinColumns = @JoinColumn(name="recipe_category"), inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if(notes != null) {
            this.notes = notes;
            this.notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        if(ingredient != null) {
            ingredient.setRecipe(this);
            this.ingredients.add(ingredient);
        }
        return this;
    }

    public Recipe addCategory(Category category) {
        if(category != null) {
            category.getRecipes().add(this);
            this.categories.add(category);
        }
        return this;
    }

}
