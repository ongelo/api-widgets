package com.example.widget.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/*
    This class is to handle convert the JSON response to PageImpl

 */
class PagedResponseHelper<T> extends PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PagedResponseHelper(@JsonProperty("content") List<T> content, @JsonProperty("number") int number, @JsonProperty("size") int size,
                            @JsonProperty("totalElements") Long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
                            @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort, @JsonProperty("first") boolean first,
                            @JsonProperty("numberOfElements") int numberOfElements) {
        super(content, PageRequest.of(number, numberOfElements), totalElements);
    }

    public PagedResponseHelper(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PagedResponseHelper(List<T> content) {
        super(content);
    }

    public PagedResponseHelper() {
        super(new ArrayList<>());
    }

}
