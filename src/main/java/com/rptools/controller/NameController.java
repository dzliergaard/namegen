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

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.rptools.city.*;
import com.rptools.name.Name;
import com.rptools.name.NameAttribute;
import com.rptools.name.NameGen;
import com.rptools.name.TrainingName;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller that manipulates {@link Name} objects
 */
@CommonsLog
@RestController("NameController")
@RequestMapping(value = {"", "name"})
public class NameController {

    private final NameGen nameGen;
    private final Gson gson;

    @Autowired
    public NameController(NameGen nameGen, Gson gson) {
        this.nameGen = nameGen;
        this.gson = gson;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView model = new ModelAndView("main");
        model.getModel().put("races", gson.toJson(Species.values()));
        return model;
    }

    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public Map<String, List<Name>> generate(@RequestParam(required = false) Integer num) {
        return ImmutableMap.of("data", nameGen.generateNames(Optional.ofNullable(num).orElse(10)));
    }

    @RequestMapping(value = "nameAttributes", method = RequestMethod.GET)
    public NameAttribute[] nameAttributes() {
        return NameAttribute.values();
    }

    @RequestMapping(value = "train", method = RequestMethod.POST)
    public TrainingName train(@RequestBody(required = false) TrainingName trainName) {
        Optional.ofNullable(trainName).ifPresent(nameGen::train);
        return nameGen.getTrainingName();
    }
}
