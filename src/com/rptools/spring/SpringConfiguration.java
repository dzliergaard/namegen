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

package com.rptools.spring;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rptools.data.GsonExclusionStrategy;

/**
 * Creates various Beans that don't have dedicated Component classes
 */
@Configuration
@ComponentScan(value = "com.rptools", scopedProxy = ScopedProxyMode.NO)
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class SpringConfiguration {
    @Bean
    public AmazonS3 s3() {
        return new AmazonS3Client();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().addSerializationExclusionStrategy(new GsonExclusionStrategy()).create();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return new JacksonFactory();
    }

    @Bean
    public ApacheHttpTransport httpTransport() {
        return new ApacheHttpTransport();
    }

    @Bean
    public String clientSecret() throws IOException {
        Resource resource = new DefaultResourceLoader().getResource("classpath:dzlier_rptools_secret");
        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line, response = "";
        while ((line = br.readLine()) != null) {
            response += line;
        }
        br.close();
        return response;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    public GoogleClientSecrets googleClientSecrets(JsonFactory jsonFactory) throws IOException {
        Resource resource = new DefaultResourceLoader().getResource("classpath:dzlier_rptools_secret");
        InputStream is = resource.getInputStream();
        return GoogleClientSecrets.load(jsonFactory, new InputStreamReader(is));
    }

    @Bean
    public File fileStoreData(ApplicationContext context) throws IOException {
        return context.getResource(".credentials").getFile();
    }

    @Bean
    public FileDataStoreFactory fileDataStoreFactory(File fileStoreData) throws IOException {
        return new FileDataStoreFactory(fileStoreData);
    }

    @Bean
    public GoogleAuthorizationCodeFlow googleFlow(
            HttpTransport transport,
            JsonFactory jsonFactory,
            GoogleClientSecrets clientSecrets,
            DataStoreFactory dataStore,
            @Value("${com.rptools.googleScopes}") String googleScopes) throws IOException {
        return new Builder(transport, jsonFactory, clientSecrets, parseScopes(googleScopes))
            .setAccessType("offline")
            .setDataStoreFactory(dataStore)
            .build();
    }

    private Collection<String> parseScopes(String googleScopes) {
        return Splitter.on(" ").splitToList(googleScopes);
    }
}
