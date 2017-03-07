package org.curtis.controller;

import org.curtis.database.DBException;
import org.curtis.database.DBSessionFactory;
import org.curtis.database.DatabaseItemManager;
import org.curtis.model.Thing;
import org.curtis.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value="/new")
    public ModelAndView addThing(HttpServletRequest request) {
        Thing thing = new Thing();

        if(request.getParameter("addThing") != null) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            if(StringUtil.isEmpty(name) || StringUtil.isEmpty(description)) {
                request.setAttribute("errorMessage", "Required field not found");
            } else {
                thing.setName(name);
                thing.setDescription(description);
                try {
                    DBSessionFactory.getInstance().getTransaction().create(thing);
                    return list();
                } catch (DBException e) {
                    request.setAttribute("errorMessage", e.getMessage());
                }
            }
        }

        return new ModelAndView("newThing", "thing", thing);
    }
}