package com.example.widget.controller;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.repository.GadgetRepository;
import com.example.widget.repository.WidgetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WidgetControllerComponentTests {
    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private GadgetRepository gadgetRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void FindAll() throws Exception {
        WidgetEntity widget1 = this.widgetRepository.save(new WidgetEntity(1L,"Fake widget 1"));
        WidgetEntity widget2 = this.widgetRepository.save(new WidgetEntity(2L,"Fake widget 2"));
        GadgetEntity gadget1 = this.gadgetRepository.save(new GadgetEntity(21L, "Fake gadget 1", 1L));
        GadgetEntity gadget2 = this.gadgetRepository.save(new GadgetEntity(22L, "Fake gadget 2", 1L));
        GadgetEntity gadget3 = this.gadgetRepository.save(new GadgetEntity(23L, "Fake gadget 3", 2L));


        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/widgets?page=0&size=2"))
                .andExpect(status().isOk()).andReturn().getResponse();

        PagedResponseHelper<WidgetResponse> widgetResponses = mapper.readValue(response.getContentAsString(), new TypeReference<PagedResponseHelper<WidgetResponse>>() {
        });

        List<WidgetResponse> widgetResponseList = widgetResponses.getContent();


        List<GadgetResponse> associatedGadgetResponseList = new ArrayList<>();
        associatedGadgetResponseList.add(new GadgetResponse(gadget1.getName(), widget1.getId()));
        associatedGadgetResponseList.add(new GadgetResponse(gadget2.getName(), widget1.getId()));
        associatedGadgetResponseList.add(new GadgetResponse(gadget3.getName(), widget1.getId()));

        assertThat(widgetResponseList.size(), equalTo(2));
        // Test Fake Widget 1
        assertThat(widgetResponseList.get(0).getName(), equalTo(widget1.getName()));
        assertThat(widgetResponseList.get(0).getGadgets().get(0).getName(), equalTo(associatedGadgetResponseList.get(0).getName()));
        assertThat(widgetResponseList.get(0).getGadgets().get(1).getName(), equalTo(associatedGadgetResponseList.get(1).getName()));
        // Test Fake Widget 2
        assertThat(widgetResponseList.get(1).getName(), equalTo(widget2.getName()));
        assertThat(widgetResponseList.get(1).getGadgets().get(0).getName(), equalTo(associatedGadgetResponseList.get(2).getName()));
    }
}
