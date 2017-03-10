package org.curtis.controller;

import org.curtis.model.Thing;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ThingServletTest {
    private HttpServletRequest request;
    private MockMvc mockMvc;
    private ThingServlet thingServlet;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        thingServlet = mock(ThingServlet.class);
        mockMvc = MockMvcBuilders.standaloneSetup(thingServlet).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void list() throws Exception {
        ThingServlet thingServlet = new ThingServlet();
        ModelAndView modelAndView = thingServlet.list();
        Map<String, Object> model = modelAndView.getModel();
        List<Thing> things = (List<Thing>)model.get("things");

        assert(things.size() > 0);
    }

    @Test
    public void newThing() throws Exception {
        ThingServlet thingServlet = new ThingServlet();
        ModelAndView modelAndView = thingServlet.newThing(request);

        assertEquals("thing", modelAndView.getViewName());
    }

    @Ignore
    @Test
    public void updateThing() throws Exception {
        String name = "New Name";
        String description = "New Description";
        mockMvc.perform(post("/thing/update/0")
                .param("name", name)
                .param("description", description))
                .andExpect(status().isOk()).andExpect(model().attribute("errorMessage", null));
    }

    @Test
    public void deleteThing() throws Exception {
        ThingServlet thingServlet = new ThingServlet();
        ModelAndView modelAndView = thingServlet.list();
        Map<String, Object> model = modelAndView.getModel();
        List<Thing> things = (List<Thing>)model.get("things");
        for(Thing thing : things) {
            thingServlet.deleteThing(thing.getId(), request);
            assertEquals("list", modelAndView.getViewName());
            break;
        }
    }

}