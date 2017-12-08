package com.rioverde.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String description;

    @ManyToMany(mappedBy = "categories")
    Set<Recipe> recipes = new HashSet<>();

    @Override
    public int hashCode() {
        // Override Lombok generated hashCode
        return super.hashCode();
    }
}
