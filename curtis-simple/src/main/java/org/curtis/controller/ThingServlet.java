package org.curtis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/thing")
public class ThingServlet {
    @RequestMapping(value = "/list")
    public ModelAndView list() {
        List<String> things = new ArrayList<>();
        things.add("thing 1");
        things.add("thing 2");

        return new ModelAndView("list", "things", things);
    }
}
