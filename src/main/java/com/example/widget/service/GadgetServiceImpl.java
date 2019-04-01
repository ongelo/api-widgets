package com.example.widget.service;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.CreateGadgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.repository.GadgetRepository;
import com.example.widget.repository.WidgetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GadgetServiceImpl implements GadgetService {
    private GadgetRepository gadgetRepository;
    private WidgetRepository widgetRepository;

    public GadgetServiceImpl(GadgetRepository gadgetRepository) {
        this.gadgetRepository = gadgetRepository;
    }

    @Override
    public void createGadget(CreateGadgetRequest createGadgetRequest) {
        this.gadgetRepository.save(new GadgetEntity(createGadgetRequest.getName(), createGadgetRequest.getWidgetId()));
    }

    @Override
    public List<GadgetResponse> findAll() {
        List<GadgetResponse> response = new ArrayList<>();

        List<GadgetEntity> entities = this.gadgetRepository.findAll();

        for(GadgetEntity entity : entities) {
            response.add(new GadgetResponse(entity.getName(), entity.getWidgetId()));
        }

        return response;
    }
}
