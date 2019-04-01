package com.example.widget.controller;

import com.example.widget.dto.CreateWidgetRequest;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.service.WidgetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = {"api/v1/widgets"})
public class WidgetController {
    private WidgetService widgetService;

    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWidget(@Valid @RequestBody CreateWidgetRequest createWidgetRequest) {
        this.widgetService.createWidget(createWidgetRequest);
    }

    // Returns associated Gadgets in the with the widgets.
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<WidgetResponse> getWidgets(Pageable pageRequest) {
        return this.widgetService.findAll(pageRequest);
    }
}
