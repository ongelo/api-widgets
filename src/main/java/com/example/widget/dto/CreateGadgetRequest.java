package com.example.widget.dto;

import javax.validation.constraints.NotNull;

public class CreateGadgetRequest {
    @NotNull
    private String name;
    @NotNull
    private Long widgetId;

    public CreateGadgetRequest() {
    }

    public CreateGadgetRequest(String name, Long widgetId) {
        this.name = name;
        this.widgetId = widgetId;
    }

    public String getName() {
        return name;
    }

    public Long getWidgetId() {
        return widgetId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidgetId(Long widgetId) {
        this.widgetId = widgetId;
    }
}
