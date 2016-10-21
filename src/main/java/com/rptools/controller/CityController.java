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

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.rptools.city.City;
import com.rptools.city.City.Species;
import com.rptools.city.CityGen;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * REST controller that manipulates {@link City} objects
 */
@RestController
@RequestMapping(value = "city")
public class CityController {

  private static final Random rand = new Random();

  private final CityGen cityGen;

  @Autowired
  public CityController(CityGen cityGen) {
    this.cityGen = cityGen;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView get() {
    ModelAndView model = new ModelAndView("main");
    return model;
  }

  @RequestMapping(value = "generate", method = RequestMethod.GET, headers = "Accept=application/json")
  public @ResponseBody String generate(
      @RequestParam(required = false) Double size,
      @RequestParam(required = false) Double diversity,
      @RequestParam(required = false) Species race) throws InvalidProtocolBufferException {
    size = Math.pow(size * size, 1.1);

    diversity = Optional.ofNullable(diversity).orElse(rand.nextInt(99) + 1.0);
    diversity = .1 + (.3 / 100) * diversity;

    return JsonFormat.printer().omittingInsignificantWhitespace()
        .print(cityGen.generateCity(size, diversity, race));
  }

  @RequestMapping(value = "races", method = RequestMethod.GET)
  public Species[] getRaces() {
    return Species.values();
  }
}
