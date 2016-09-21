package com.rptools.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Returns the list of table files available at /data/tables/
 */
@RestController("TableController")
@RequestMapping("tables")
@CommonsLog
public class TableController {
  private ResourcePatternResolver resolver;

  public TableController() {
    resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
  }

  @RequestMapping(method = RequestMethod.GET)
  public Map<String, List<String>> getTableURLs() throws IOException {
    List<Resource> resources = Lists.newArrayList(resolver.getResources("**/*.txt"));
    return ImmutableMap.of("data", resources.stream().map(Resource::getFilename).collect(Collectors.toList()));
  }
}
