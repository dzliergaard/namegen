package com.rptools.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.rptools.table.RPTable;
import com.rptools.table.TableReader;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@CommonsLog
@RestController("TablesController")
@RequestMapping("tables")
public class TablesController {
  private final TableReader tableReader;

  public TablesController(TableReader tableReader) {
    this.tableReader = tableReader;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView main() {
    return new ModelAndView("main");
  }

  @RequestMapping(value = "list", method = RequestMethod.GET)
  public String list() {
    return serializeTable(tableReader.getTables());
  }

  private String serializeTable(RPTable table) {
    try {
      return JsonFormat.printer().print(table);
    } catch (InvalidProtocolBufferException e) {
      log.error("Exception serializing RPTable object " + table.getName(), e);
      return "";
    }
  }
}
