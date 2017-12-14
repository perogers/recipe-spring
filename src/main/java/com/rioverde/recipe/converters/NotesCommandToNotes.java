package com.rioverde.recipe.converters;

import com.rioverde.recipe.commands.NotesCommand;
import com.rioverde.recipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes>{

    @Override
    public Notes convert(NotesCommand command) {
        if(command == null) {
            return null;
        }

        Notes notes = new Notes();
        notes.setId(command.getId());
        notes.setRecipeNotes(command.getRecipeNotes());

        return notes;
    }
}
