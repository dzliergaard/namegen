package com.rptools.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.rptools.name.NameAttribute;
import com.rptools.name.NameUtils;
import com.rptools.name.TrainingName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.rptools.name.Name;
import com.rptools.util.Logger;

@RestController("NameController")
@RequestMapping(value = { "name", "" })
public class NameController {
    private static Logger log = Logger.getLogger(NameController.class);
    private static final String ATTR_NAMES = "names";
    private static final String ATTR_TRAINING_NAME = "trainingName";
    private static final String ATTR_NAME_ATTRIBUTES = "nameAttributes";

    private NameUtils nameUtils;

    @Autowired
    public NameController(NameUtils nameUtils) {
        this.nameUtils = nameUtils;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getNames() {
        ModelAndView mav = new ModelAndView("name.jsp");
        List<Name> names = nameUtils.get();
        log.info("Names retrieved from datastore: " + names);
        mav.addObject(ATTR_NAMES, names);
        mav.addObject(ATTR_TRAINING_NAME, nameUtils.getTrainingName());
        mav.addObject(ATTR_NAME_ATTRIBUTES, NameAttribute.asList());
        return mav;
    }

    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public List<Name> generate(@RequestParam(required = false, value = "numNames") Integer numNames) {
        if (numNames == null) {
            numNames = 10;
        }

        return nameUtils.generateNames(numNames);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody(required = true) Name name, HttpServletResponse response) {
        try {
            if (name == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } catch (IOException ioe) {
            log.info("Error sending error response for invalid input");
            return;
        }

        nameUtils.delete(name);
    }

    @RequestMapping(value = "clear", method = RequestMethod.GET)
    public void clear() {
        for (Name name : nameUtils.get()) {
            nameUtils.delete(name);
        }
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody Name save(@RequestBody(required = false) Name name, HttpServletResponse response) {
        if (name == null) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (IOException e) {
                log.error("Error sending error response for invalid input");
            }
            return null;
        }
        nameUtils.save(name);
        return name;
    }

    @RequestMapping(value = "train", method = RequestMethod.POST)
    public @ResponseBody TrainingName train(@RequestBody TrainingName trainName){
        nameUtils.train(trainName);
        return nameUtils.getTrainingName();
    }
}
