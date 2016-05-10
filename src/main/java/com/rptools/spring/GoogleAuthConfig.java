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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;

/**
 * Beans required by GoogleAuthorizationCodeFlow.Builder and GoogleCredential.Builder
 */
@Configuration
public class GoogleAuthConfig {
    @Bean
    public JsonFactory jsonFactory() {
        return new JacksonFactory();
    }

    @Bean
    public ApacheHttpTransport httpTransport() {
        return new ApacheHttpTransport();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    public GoogleClientSecrets googleClientSecrets(JsonFactory jsonFactory) throws IOException {
        Resource resource = new DefaultResourceLoader().getResource("classpath:rptools_secret");
        InputStream is = resource.getInputStream();
        return GoogleClientSecrets.load(jsonFactory, new InputStreamReader(is));
    }

    @Bean
    public File fileStoreData() throws IOException {
        return Paths.get("data", "credentials").toFile();
    }

    @Bean
    public DataStore<StoredCredential> fileDataStoreFactory(File fileStoreData) throws IOException {
        return StoredCredential.getDefaultDataStore(new FileDataStoreFactory(fileStoreData));
    }
}
