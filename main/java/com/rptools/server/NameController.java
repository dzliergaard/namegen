package com.rptools.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.appengine.api.users.User;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.rptools.items.Name;
import com.rptools.items.NameAttribute;
import com.rptools.items.TrainingName;
import com.rptools.util.Logger;
import com.rptools.util.NameUtils;
import com.rptools.util.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping(value={"name", ""}, method={RequestMethod.GET, RequestMethod.HEAD})
public class NameController {
    private static Logger log = Logger.getLogger(NameController.class);
    @Autowired Provider<User> userProvider;

    private static final String ATTR_NAMES = "names";
    private static final String ATTR_TRAINING_NAME = "trainingName";
    private static final String ATTR_TRAINING_ATTRIBUTES = "trainingAttributes";

    private static final Function<NameAttribute, String> attributeToString = new Function<NameAttribute, String>(){
        @Override
        public String apply(NameAttribute attribute){
            return attribute.name();
        }
    };

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView getNames() throws JsonProcessingException {
        ModelAndView mav = new ModelAndView("name.jsp");
        mav.addObject(ATTR_TRAINING_NAME, NameUtils.getTrainingName());
        mav.addObject(ATTR_TRAINING_ATTRIBUTES, new ObjectMapper().writeValueAsString(NameAttribute.values()));
        if(userProvider.get() == null){
            mav.addObject(ATTR_NAMES, Lists.newArrayList());
            return mav;
        }
        List<Name> names = NameUtils.get(userProvider.get());
        String userName = userProvider.has() ? userProvider.get().getNickname() : "null";
        log.info("Names retrieved from datastore for user %s: " + names, userName);
        mav.addObject(ATTR_NAMES, names);
        return mav;
    }

    @RequestMapping(value="generate", method=RequestMethod.GET, headers="Accept=application/json")
    public @ResponseBody List<Name> generate(@RequestParam(required=false) Integer numNames,
                                             @RequestParam(required=false) NameAttribute attribute,
                                             HttpServletResponse response){
        if(numNames == null) {
            numNames = 10;
        }

        return NameUtils.generateNames(numNames, userProvider.get(), attribute);
    }

    @RequestMapping(value="delete", method=RequestMethod.POST)
    public void delete(@RequestBody(required=true) Name name, HttpServletResponse response){
        if(!userProvider.has()){ return; }

        try {
            if (name == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } catch(IOException ioe){
            log.info("Error sending error response for invalid input");
            return;
        }

        NameUtils.delete(name);
    }

    @RequestMapping(value="clear", method=RequestMethod.GET)
    public void clear(){
        if(!userProvider.has()){ return; }

        for(Name name : NameUtils.get(userProvider.get())){
            NameUtils.delete(name);
        }
    }

    @RequestMapping(value="save", method= RequestMethod.POST)
    public @ResponseBody Name save(@RequestBody(required=false) Name name, HttpServletResponse response){
        if(name == null || !userProvider.has()){
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (IOException e) {
                log.error("Error sending error response for invalid input");
            }
            return null;
        }
        name.setUser(userProvider.get());
        NameUtils.save(name);
        return name;
    }

    @RequestMapping(value="train", method=RequestMethod.POST)
    public @ResponseBody TrainingName train(@RequestBody TrainingName name){
        NameUtils.train(name);
        return NameUtils.getTrainingName();
    }
}
