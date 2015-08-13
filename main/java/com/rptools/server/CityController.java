package com.rptools.server;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.User;
import com.rptools.city.City;
import com.rptools.city.CityTemplate;
import com.rptools.city.Diversity;
import com.rptools.city.Race;
import com.rptools.util.CityUtils;
import com.rptools.util.Provider;

@Controller
@RequestMapping(value = "city")
public class CityController {
    private CityUtils cityUtils;
    private Provider<User> userProvider;

    @Autowired
    public CityController(CityUtils cityUtils, Provider<User> userProvider) {
        this.cityUtils = cityUtils;
        this.userProvider = userProvider;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getNames() {
        return new ModelAndView("city.jsp");
    }

    @RequestMapping(value = "generate", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody City generate(@RequestParam(required = false) CityTemplate size, @RequestParam(
        required = false) Diversity diversity, @RequestParam(required = false) Race race) {
        size = size == null ? CityTemplate.rand() : size;
        diversity = diversity == null ? Diversity.rand() : diversity;
        race = race == null ? Race.rand() : race;

        return cityUtils.generateCity(size, diversity, race);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody(required = true) City city, HttpServletResponse response) {
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody City save(@RequestBody(required = false) City city, HttpServletResponse response) {
        return null;
    }
}
