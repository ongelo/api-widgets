package com.example.widget.service;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.CreateWidgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.repository.GadgetRepository;
import com.example.widget.repository.WidgetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;


class WidgetServiceImplTests {
    private WidgetRepository widgetRepository = mock(WidgetRepository.class);
    private GadgetRepository gadgetRepository = mock(GadgetRepository.class);
    private WidgetServiceImpl widgetService = new WidgetServiceImpl(widgetRepository, gadgetRepository);
    private GadgetServiceImpl gadgetService = new GadgetServiceImpl(gadgetRepository);

    @Test
    void FindAll_ReturnsAPagedListOfWidgetsWithGadgets() {
        Page<WidgetEntity> widgetsWithPageZeroSizeThree = new PageImpl<>(Arrays.asList(
                new WidgetEntity(1L, "Fake Widget 1"),
                new WidgetEntity(2L, "Fake widget 2"),
                new WidgetEntity(3L, "Fake widget 3")
        ));

        when(widgetRepository.findAll(PageRequest.of(0, 3))).thenReturn(widgetsWithPageZeroSizeThree);

        when(gadgetRepository.findAll()).thenReturn(Arrays.asList(
                new GadgetEntity(21L, "Fake gadget 21", 1L),
                new GadgetEntity(22L, "Fake gadget 22", 1L),
                new GadgetEntity(23L, "Fake gadget 23", 2L),
                new GadgetEntity(24L, "Fake gadget 24", 3L)
        ));

        Page<WidgetResponse> pagedWidgets = widgetService.findAll(PageRequest.of(0, 3));
        List<WidgetResponse> widgets = pagedWidgets.getContent();
        List<GadgetResponse> gadgets = gadgetService.findAll();

        assertThat(widgets.size(), equalTo(3));
        assertThat(gadgets.size(), equalTo(4));
        assertThat(widgets.get(0).getGadgets().get(0).getName(), equalTo(gadgets.get(0).getName()));
        assertThat(widgets.get(0).getGadgets().get(1).getName(), equalTo(gadgets.get(1).getName()));
        assertThat(widgets.get(1).getGadgets().get(0).getName(), equalTo(gadgets.get(2).getName()));
        assertThat(widgets.get(2).getGadgets().get(0).getName(), equalTo(gadgets.get(3).getName()));

        verify(widgetRepository).findAll(PageRequest.of(0, 3));
    }

    @Test
    void FindAll_ReturnsEmptyListOfWidgets() {
        when(widgetRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<>(new ArrayList<>()));

        Page<WidgetResponse> widgets = widgetService.findAll(PageRequest.of(0, 5));

        assertThat(widgets.getTotalElements(), equalTo(0L));

        verify(widgetRepository).findAll(PageRequest.of(0, 5));
    }

    @Test
    void Create_CreatesNewWidget() {
        CreateWidgetRequest createWidgetRequest = new CreateWidgetRequest("Cool Widget");
        WidgetEntity widgetEntity = new WidgetEntity("Cool widget");
        WidgetEntity widgetEntityWithId = new WidgetEntity(1L, widgetEntity.getName());

        when(widgetRepository.save(widgetEntity)).thenReturn(widgetEntityWithId);

        widgetService.createWidget(createWidgetRequest);


        verify(widgetRepository).save(any());
    }
}
