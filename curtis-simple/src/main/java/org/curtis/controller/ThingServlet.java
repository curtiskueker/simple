package org.curtis.controller;

import org.curtis.database.DBException;
import org.curtis.database.DBSessionFactory;
import org.curtis.database.DatabaseItemManager;
import org.curtis.model.Thing;
import org.curtis.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @RequestMapping(value="/update/{id}")
    public ModelAndView updateThing(@Valid @ModelAttribute("thing") Thing newThing, BindingResult result, @PathVariable Integer id, HttpServletRequest request) {
        Thing thing = new Thing();

        try {
            if (id > 0) {
                thing = DatabaseItemManager.getInstance().find(Thing.class, id);
            }
        } catch (DBException e) {
            request.setAttribute("errorMessage", e.getMessage());
            return list();
        }

        ModelAndView defaultView = new ModelAndView("thing", "thing", thing);

        if(request.getParameter("updateThing") != null) {
            if(thing == null) {
                return list();
            }

            if(result.hasFieldErrors()) {
                String errorMessage = "";
                List<FieldError> fieldErrors = result.getFieldErrors();
                for(FieldError fieldError : fieldErrors) {
                    errorMessage += fieldError.getField() + ": " + fieldError.getDefaultMessage() + " ";
                }
                request.setAttribute("errorMessage", errorMessage);
                defaultView.addObject("thing", newThing);
                return defaultView;
            }

            thing.setName(newThing.getName());
            thing.setDescription(newThing.getDescription());
            try {
                if (!thing.isPersisted()) {
                    DBSessionFactory.getInstance().getTransaction().create(thing);
                }
                return list();
            } catch (DBException e) {
                request.setAttribute("errorMessage", e.getMessage());
            }
        }

        return defaultView;
    }

    @RequestMapping(value="/delete/{id}")
    public ModelAndView deleteThing(@PathVariable Integer id, HttpServletRequest request) {
        try {
            Thing thing = DatabaseItemManager.getInstance().find(Thing.class, id);
            if(thing != null) {
                DBSessionFactory.getInstance().getTransaction().delete(thing);
            }
        } catch (DBException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }
        return list();
    }
}
