package com.rptools.city;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rptools.util.Logger;

@Component
public class CityFileParser {
    private final Logger log = Logger.getLogger(CityFileParser.class);
    private static final Gson gson = new Gson();

    public Cities parseCityFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String str, cityDataStr = "";
            while ((str = br.readLine()) != null) {
                cityDataStr += str.trim();
            }
            br.close();

            return gson.fromJson(cityDataStr, Cities.class);
        } catch (IOException e) {
            log.warning("Error parsing name data: %e", e.getMessage());
        }

        return new Cities();
    }
}
