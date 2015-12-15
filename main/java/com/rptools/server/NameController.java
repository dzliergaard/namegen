package com.rptools.server;

import java.util.List;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Optional;
import com.rptools.name.Name;
import com.rptools.name.NameAttribute;
import com.rptools.name.NameUtils;
import com.rptools.name.TrainingName;
import com.rptools.util.Logger;

@RestController("NameController")
@RequestMapping(value = { "name", "" })
public class NameController extends EntityServerBase<Name> {
    private static Logger log = Logger.getLogger(NameController.class);
    private static final String ATTR_SAVED_NAMES = "savedNames";
    private static final String ATTR_GENERATED_NAMES = "generatedNames";
    private static final String ATTR_TRAINING_NAME = "trainingName";
    private static final String ATTR_NAME_ATTRIBUTES = "nameAttributes";

    private NameUtils nameUtils;

    @Autowired
    public NameController(NameUtils nameUtils) {
        this.nameUtils = nameUtils;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView mav = new ModelAndView("name.jsp");
        List<Name> names = nameUtils.get();
        log.info("Names retrieved from datastore: " + names);
        mav.addObject(ATTR_SAVED_NAMES, names);
        mav.addObject(ATTR_GENERATED_NAMES, nameUtils.generateNames(10));
        mav.addObject(ATTR_TRAINING_NAME, nameUtils.getTrainingName());
        mav.addObject(ATTR_NAME_ATTRIBUTES, NameAttribute.asList());
        return mav;
    }

    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public @ResponseBody List<Name> generate(@RequestParam(required = false) Integer num) {
        return nameUtils.generateNames(Optional.fromNullable(num).or(10));
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody Name save(@NonNull @RequestBody(required = false) Name name) {
        nameUtils.save(name);
        return name;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@NonNull @RequestBody(required = true) Name name) {
        nameUtils.delete(name);
    }

    @RequestMapping(value = "train", method = RequestMethod.POST)
    public @ResponseBody TrainingName train(@RequestBody TrainingName trainName) {
        nameUtils.train(trainName);
        return nameUtils.getTrainingName();
    }

    @RequestMapping(value = "clear", method = RequestMethod.GET)
    public void clear() {
        for (Name name : nameUtils.get()) {
            nameUtils.delete(name);
        }
    }
}
