package com.rptools.server.city;

import com.google.appengine.api.users.User;
import com.rptools.shared.items.City;
import com.rptools.shared.items.Name;
import com.rptools.shared.util.CityUtils;
import com.rptools.shared.util.Logger;
import com.rptools.shared.util.NameUtils;
import com.rptools.util.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value="city")
public class CityController {
    private static Logger log = Logger.getLogger(CityController.class);
    @Autowired Provider<User> userProvider;

    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView getNames(){
        return new ModelAndView("city.jsp");
    }

    @RequestMapping(value="generate", method=RequestMethod.GET, headers="Accept=application/json")
    public @ResponseBody City generate(@RequestParam(required=false) City.CityTemplate size,
                                       @RequestParam(required=false) City.Diversity diversity,
                                       @RequestParam(required=false) City.Race race){
        size = size == null ? City.CityTemplate.rand() : size;
        diversity = diversity == null ? City.Diversity.rand() : diversity;
        race = race == null ? City.Race.rand() : race;

        return CityUtils.generateCity(size, diversity, race, userProvider.get());
    }

    @RequestMapping(value="delete", method=RequestMethod.POST)
    public void delete(@RequestBody(required=true) Name name, HttpServletResponse response){
    }

    @RequestMapping(value="save", method= RequestMethod.POST)
    public @ResponseBody Name save(@RequestBody(required=false) String name, HttpServletResponse response){
        return null;
    }
}
