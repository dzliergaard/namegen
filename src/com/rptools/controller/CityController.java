/*
 * RPToolkit - Tools to assist Role-Playing Game masters and players
 * Copyright (C) 2016 Dane Zeke Liergaard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.rptools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rptools.city.City;
import com.rptools.city.CityTemplate;
import com.rptools.city.Diversity;
import com.rptools.city.Race;

/**
 * REST controller that manipulates {@link City} objects
 */
@Controller
@RequestMapping(value = "city")
public class CityController {

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
        return null;
        // return cityUtils.generateCity(
        // Optional.fromNullable(size).or(CityTemplate.rand()),
        // Optional.fromNullable(diversity).or(Diversity.rand()),
        // Optional.fromNullable(race).or(Race.rand()));
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody(required = true) City city) {
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody City save(@RequestBody(required = false) City city) {
        return null;
    }
}
