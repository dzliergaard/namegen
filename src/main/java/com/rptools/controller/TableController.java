package com.rptools.controller;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Returns the list of table files available at /data/tables/
 */
@RestController("TableController")
@RequestMapping("tables")
public class TableController {

  @RequestMapping(method = RequestMethod.GET)
  public Map<String, List<String>> getTableURLs() {
    return null;
  }
}
