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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.rptools.annotation.RequiresGoogleAuth;
import com.rptools.data.UserSavedContent;
import com.rptools.name.Name;
import com.rptools.name.NameAttribute;
import com.rptools.name.NameGen;
import com.rptools.name.TrainingName;

/**
 * REST controller that manipulates {@link Name} objects
 */
@CommonsLog
@RestController("NameController")
@RequestMapping(value = { "name" })
public class NameController {
    private static final String ATTR_NAME_ATTRIBUTES = "nameAttributes";
    private static final String ATTR_TRAINING_NAME = "trainingName";

    private final NameGen nameGen;
    private final UserSavedContent userSavedContent;

    @Autowired
    public NameController(NameGen nameGen, UserSavedContent userSavedContent) {
        this.nameGen = nameGen;
        this.userSavedContent = userSavedContent;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView mav = new ModelAndView("main");

        mav.addObject(ATTR_TRAINING_NAME, nameGen.getTrainingName());
        mav.addObject(ATTR_NAME_ATTRIBUTES, NameAttribute.asList());
        return mav;
    }

    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public Map<String, List<Name>> generate(@RequestParam(required = false) Integer num) {
        return ImmutableMap.of("data", nameGen.generateNames(Optional.ofNullable(num).orElse(10)));
    }

    @RequiresGoogleAuth
    @RequestMapping(method = RequestMethod.GET, value = "getNames")
    public Collection<Name> getNames(HttpServletRequest request, HttpServletResponse response) {
        userSavedContent.refreshContent();
        return userSavedContent.getNames();
    }

    @RequestMapping(value = "train", method = RequestMethod.POST)
    public TrainingName train(@RequestBody TrainingName trainName) {
        nameGen.train(trainName);
        return nameGen.getTrainingName();
    }

    @RequiresGoogleAuth
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Name save(HttpServletRequest request, HttpServletResponse response, @RequestBody Name name) {
        return userSavedContent.addName(name);
    }

    @RequiresGoogleAuth
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, @RequestBody Name name) {
        userSavedContent.deleteName(name);
    }

    @RequiresGoogleAuth
    @RequestMapping(value = "clear", method = RequestMethod.GET)
    public void clear(HttpServletRequest request, HttpServletResponse response) {
        userSavedContent.clearNames();
    }
}
