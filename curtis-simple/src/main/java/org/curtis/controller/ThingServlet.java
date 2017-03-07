package org.curtis.controller;

import org.curtis.database.DBException;
import org.curtis.database.DatabaseItemManager;
import org.curtis.model.Thing;
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
        List<Thing> things = new ArrayList<>();

        try {
            things = DatabaseItemManager.getInstance().findAll(Thing.class);
        } catch (DBException e) {
            e.printStackTrace();
            return new ModelAndView("list", "errorMessage", e.getMessage());
        }

        return new ModelAndView("list", "things", things);
    }
}
