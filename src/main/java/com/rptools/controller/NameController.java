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
import com.rptools.name.NameAttribute;
import com.rptools.name.NameGen;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller that manipulates Name strings
 */
@CommonsLog
@RestController("NameController")
@RequestMapping("name")
public class NameController {

  private final NameGen nameGen;

  @Autowired
  public NameController(NameGen nameGen) {
    this.nameGen = nameGen;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView get() {
    return new ModelAndView("main");
  }

  @RequestMapping(value = "generate", method = RequestMethod.GET)
  public Map<String, List<String>> generate(@RequestParam(required = false) Integer num) {
    return ImmutableMap.of("data", nameGen.generateNames(Optional.ofNullable(num).orElse(10)));
  }

  @RequestMapping(value = "nameAttributes", method = RequestMethod.GET)
  public NameAttribute[] nameAttributes() {
    return NameAttribute.values();
  }
}
