package com.rptools.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.labs.repackaged.com.google.common.base.Optional;
import com.rptools.city.*;

@Controller
@RequestMapping(value = "city")
public class CityController extends EntityServerBase<City> {
    private final CityUtils cityUtils;

    @Autowired
    public CityController(CityUtils cityUtils) {
        this.cityUtils = cityUtils;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView model = new ModelAndView("city.jsp");
        model.addObject("speciesValues", Race.asList());
        model.addObject("diversityValues", Diversity.asList());
        model.addObject("sizeValues", CityTemplate.asList());
        return model;
    }

    @RequestMapping(value = "generate", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody City generate(
            @RequestParam(required = false) CityTemplate size,
            @RequestParam(required = false) Diversity diversity,
            @RequestParam(required = false) Race race) {
        return cityUtils.generateCity(
            Optional.fromNullable(size).or(CityTemplate.rand()),
            Optional.fromNullable(diversity).or(Diversity.rand()),
            Optional.fromNullable(race).or(Race.rand()));
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody(required = true) City city) {
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody City save(@RequestBody(required = false) City city) {
        return null;
    }
}
