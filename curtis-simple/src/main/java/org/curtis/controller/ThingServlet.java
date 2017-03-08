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
    public ModelAndView updateThing(HttpServletRequest request) {
        Thing thing = new Thing();
        if(request.getParameter("thingId") != null) {
            Integer thingId = Integer.parseInt(request.getParameter("thingId"));
            try {
                thing = DatabaseItemManager.getInstance().find(Thing.class, thingId);
            } catch (DBException e) {
                request.setAttribute("errorMessage", e.getMessage());
                return list();
            }
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

    @RequestMapping(value="/delete")
    public ModelAndView deleteThing(HttpServletRequest request) {
        if(request.getParameter("thingId") != null) {
            Integer thingId = Integer.parseInt(request.getParameter("thingId"));
            try {
                Thing thing = DatabaseItemManager.getInstance().find(Thing.class, thingId);
                if(thing != null) {
                    DBSessionFactory.getInstance().getTransaction().delete(thing);
                }
            } catch (DBException e) {
                request.setAttribute("errorMessage", e.getMessage());
            }
        }
        return list();
    }
}
