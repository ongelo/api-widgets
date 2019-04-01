package com.example.widget.service;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.CreateWidgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.repository.WidgetRepository;
import com.example.widget.repository.GadgetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class WidgetServiceImpl implements WidgetService {
    private WidgetRepository widgetRepository;
    private GadgetRepository gadgetRepository;

    public WidgetServiceImpl(WidgetRepository widgetRepository, GadgetRepository gadgetRepository) {
        this.widgetRepository = widgetRepository;
        this.gadgetRepository = gadgetRepository;
    }

    @Override
    public void createWidget(CreateWidgetRequest createWidgetRequest) {
        this.widgetRepository.save(new WidgetEntity(createWidgetRequest.getName()));
    }

    /*
        Returns a Page object of WidgetResponse objects with associated gadgets in the content
     */
    @Override
    public Page<WidgetResponse> findAll(Pageable pageRequest) {
        List<WidgetResponse> response = new ArrayList<>();

        List<WidgetEntity> widget_entities = this.widgetRepository.findAll(pageRequest).getContent();
        List<GadgetEntity> gadget_entities = this.gadgetRepository.findAll(); // all the potential gadgets

        // Compares all the widgets with potential gadgets and adds to response array
        for (WidgetEntity widget_entity : widget_entities) {
            List<GadgetResponse> associated_gadgets = new ArrayList<>(); // create an array of gadgets for each widget
            for(GadgetEntity gadget_entity : gadget_entities) {
                if(widget_entity.getId() == gadget_entity.getWidgetId()) { // check if associated
                    associated_gadgets.add(new GadgetResponse(gadget_entity.getName(), gadget_entity.getWidgetId()));
                }
            }
            response.add(new WidgetResponse(widget_entity.getName(), associated_gadgets));
        }

        return new PageImpl<>(response); // return response list as a Page object
    }
}
