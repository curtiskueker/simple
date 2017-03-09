package org.curtis.controller;

import org.curtis.database.DBException;
import org.curtis.database.DBSessionFactory;
import org.curtis.database.DatabaseItemManager;
import org.curtis.model.Thing;
import org.curtis.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value="/thing")
public class ThingServlet {
    @RequestMapping(value = "/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("list");

        try {
            List<Thing> things = DatabaseItemManager.getInstance().findAll(Thing.class);
            modelAndView.addObject("things", things);
        } catch (DBException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }

        return modelAndView;
    }

    @RequestMapping(value="/update")
    public ModelAndView newThing(HttpServletRequest request) {
        Thing thing = new Thing();

        return new ModelAndView("thing", "thing", thing);
    }

    @RequestMapping(value="/update/{thingId}")
    public ModelAndView updateThing(@PathVariable Integer thingId, HttpServletRequest request) {
        Thing thing = new Thing();

        try {
            if (thingId > 0) {
                thing = DatabaseItemManager.getInstance().find(Thing.class, thingId);
            }
        } catch (DBException e) {
            request.setAttribute("errorMessage", e.getMessage());
            return list();
        }

        if(request.getParameter("updateThing") != null) {
            if(thing == null) {
                return list();
            }

            String name = request.getParameter("name");
            String description = request.getParameter("description");

            if(StringUtil.isEmpty(name) || StringUtil.isEmpty(description)) {
                request.setAttribute("errorMessage", "Required field not found");
            } else {
                thing.setName(name);
                thing.setDescription(description);
                try {
                    if (!thing.isPersisted()) {
                        DBSessionFactory.getInstance().getTransaction().create(thing);
                    }
                    return list();
                } catch (DBException e) {
                    request.setAttribute("errorMessage", e.getMessage());
                }
            }
        }

        return new ModelAndView("thing", "thing", thing);
    }

    @RequestMapping(value="/delete/{thingId}")
    public ModelAndView deleteThing(@PathVariable Integer thingId, HttpServletRequest request) {
        try {
            Thing thing = DatabaseItemManager.getInstance().find(Thing.class, thingId);
            if(thing != null) {
                DBSessionFactory.getInstance().getTransaction().delete(thing);
            }
        } catch (DBException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }
        return list();
    }
}
