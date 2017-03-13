package org.curtis.controller;

import org.curtis.model.Thing;
import org.curtis.test.MockedHttpServletRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ThingServletTest {
    private ThingServlet thingServlet;
    private static MockedHttpServletRequest mockedRequest;
    private static HttpServletResponse mockedResponse;
    private static Validator validator;

    @Before
    public void setUp() throws Exception {
        mockedRequest = new MockedHttpServletRequest(null);
        mockedResponse = Mockito.mock(HttpServletResponse.class);
        thingServlet = mock(ThingServlet.class);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
        ModelAndView modelAndView = thingServlet.newThing(mockedRequest);

        assertEquals("thing", modelAndView.getViewName());
    }

    @Test
    public void updateThing() throws Exception {
        String name = "New Name";
        String description = "New Description";
        mockedRequest.setParameter("name", name);
        mockedRequest.setParameter("description", description);

        Thing thing = new Thing();
        thing.setName(name);
        thing.setDescription(description);

        Set<ConstraintViolation<Thing>> violations = validator.validate(thing);
        assertTrue(violations.isEmpty()? "" : violations.iterator().next().getMessage(), violations.isEmpty());

        BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), Thing.class.getName());
        thingServlet.updateThing(thing, bindingResult, 14, mockedRequest);
    }

    @Test
    public void deleteThing() throws Exception {
        ThingServlet thingServlet = new ThingServlet();
        ModelAndView modelAndView = thingServlet.list();
        Map<String, Object> model = modelAndView.getModel();
        List<Thing> things = (List<Thing>)model.get("things");
        for(Thing thing : things) {
            thingServlet.deleteThing(thing.getId(), mockedRequest);
            assertEquals("list", modelAndView.getViewName());
            break;
        }
    }

}