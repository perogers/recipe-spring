package com.rioverde.recipe.services;

import com.rioverde.recipe.commands.UnitOfMeasureCommand;
import com.rioverde.recipe.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();

}
