package com.example.widget.service;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.dto.CreateGadgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.repository.GadgetRepository;
import com.example.widget.repository.WidgetRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;


class GadgetServiceImplTests {
    private GadgetRepository gadgetRepository = mock(GadgetRepository.class);
    private GadgetServiceImpl gadgetService = new GadgetServiceImpl(gadgetRepository);

    @Test
    void FindAll_ReturnsAListOfGadgets() {
        when(gadgetRepository.findAll()).thenReturn(Arrays.asList(
                new GadgetEntity(21L, "Fake gadget 21", 1L),
                new GadgetEntity(22L, "Fake gadget 22", 2L)
        ));

        List<GadgetResponse> gadgets = gadgetService.findAll();

        assertThat(gadgets.size(), equalTo(2));

        verify(gadgetRepository).findAll();
    }

    @Test
    void FindAll_ReturnsEmptyListOfGadgets() {
        when(gadgetRepository.findAll()).thenReturn(new ArrayList<>());

        List<GadgetResponse> gadgets = gadgetService.findAll();

        assertThat(gadgets.size(), equalTo(0));

        verify(gadgetRepository).findAll();
    }

    @Test
    void Create_CreatesNewGadget() {
        CreateGadgetRequest createGadgetRequest = new CreateGadgetRequest("Cool Gadget", 21L);
        GadgetEntity gadgetEntity = new GadgetEntity("Cool gadget", 1L);
        GadgetEntity gadgetEntityWithId = new GadgetEntity(21L, gadgetEntity.getName(), gadgetEntity.getWidgetId());

        when(gadgetRepository.save(gadgetEntity)).thenReturn(gadgetEntityWithId);

        gadgetService.createGadget(createGadgetRequest);

        verify(gadgetRepository).save(any());

    }


}
