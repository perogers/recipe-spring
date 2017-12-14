package com.rioverde.recipe.converters;


import com.rioverde.recipe.commands.NotesCommand;
import com.rioverde.recipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

    @Override
    public NotesCommand convert(Notes notes) {
        if(notes == null) {
            return null;
        }

        NotesCommand command = new NotesCommand();
        command.setId(notes.getId());
        command.setRecipeNotes(notes.getRecipeNotes());

        return command;
    }
}
