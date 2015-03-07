package com.rptools.server;

import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;
import com.rptools.items.Name;
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

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView getNames(){
        ModelAndView mav = new ModelAndView("name.jsp");
        mav.addObject(ATTR_TRAINING_NAME, NameUtils.getTrainingName());
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
    public @ResponseBody List<Name> generate(@RequestParam(required=false) Integer numNames, HttpServletResponse response){
        if(numNames == null){
            numNames = 10;
        }

        return NameUtils.generateNames(numNames, userProvider.get());
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

    @RequestMapping(value = "createTable", method = RequestMethod.GET)
    public boolean createNamesTable() throws ClassNotFoundException, SQLException {
        String url;
//        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            // Connecting from App Engine.
            // Load the class that provides the "jdbc:google:mysql://"
            // prefix.
//            Class.forName("com.mysql.jdbc.GoogleDriver");
            url = "jdbc:mysql://dzlier-rptools:rpdata?user=root";
//        } else {
//            // You may also assign an IP Address from the access control
//            // page and use it to connect from an external network.
            Class.forName("com.mysql.jdbc.Driver");
//            url = "jdbc:mysql://127.0.0.1:3306/rpdata?user=root";
//        }

        Connection conn = DriverManager.getConnection(url);
        conn.close();
//        boolean createD = conn.createStatement().execute("CREATE DATABASE names");
//        boolean createT = conn.createStatement().execute(
//                "CREATE TABLE names.SAVED ("
//                        + "ENTRY_ID INT NOT NULL AUTO_INCREMENT,"
//                        + "NAME VARCHAR(255),"
//                        + "USER VARCHAR(255))");

        return true;
    }
}
