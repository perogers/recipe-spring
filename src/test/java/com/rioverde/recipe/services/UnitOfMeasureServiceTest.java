package com.rioverde.recipe.services;

import com.rioverde.recipe.commands.UnitOfMeasureCommand;
import com.rioverde.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.rioverde.recipe.domain.UnitOfMeasure;
import com.rioverde.recipe.respositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class UnitOfMeasureServiceTest {

    UnitOfMeasureServiceImpl unitOfMeasureService;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();


    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void testGetAll() {

        // Given
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(new Long(1L));
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom1.setId(new Long(2L));
        Set<UnitOfMeasure> uomData = new HashSet<>();
        uomData.add(uom1);
        uomData.add(uom2);

        // When
        when(unitOfMeasureRepository.findAll()).thenReturn(uomData);

        // Then
        Set<UnitOfMeasureCommand> uoms = unitOfMeasureService.listAllUoms();

        assertEquals(uoms.size(), 2);
        verify(unitOfMeasureRepository, times(1)).findAll();
    }

}
