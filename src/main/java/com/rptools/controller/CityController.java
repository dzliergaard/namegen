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

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rptools.city.City;
import com.rptools.city.CityGen;
import com.rptools.city.CityTemplate;
import com.rptools.city.CityVariables;
import com.rptools.city.Diversity;
import com.rptools.city.Species;

/**
 * REST controller that manipulates {@link City} objects
 */
@RestController
@RequestMapping(value = "city")
public class CityController {
    private final CityGen cityGen;

    @Autowired
    public CityController(CityGen cityGen) {
        this.cityGen = cityGen;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
        return new ModelAndView("main");
    }

    @RequestMapping(value = "generate", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody City generate(
            @RequestParam(required = false) CityTemplate size,
            @RequestParam(required = false) Diversity diversity,
            @RequestParam(required = false) Species species) {
        return cityGen.generateCity(
            Optional.ofNullable(size).orElse(CityTemplate.rand()),
            Optional.ofNullable(diversity).orElse(Diversity.rand()),
            Optional.ofNullable(species).orElse(Species.rand()));
    }

    @RequestMapping(value = "variableChoices", method = RequestMethod.GET)
    public CityVariables getCityVariables() {
        return new CityVariables();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody(required = true) City city) {
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody City save(@RequestBody(required = false) City city) {
        return null;
    }
}
