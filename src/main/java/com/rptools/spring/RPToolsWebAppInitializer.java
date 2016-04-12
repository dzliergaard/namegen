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

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Spring web application configuration
 */
public class RPToolsWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { SpringConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
    // @Override
    // protected void registerContextLoaderListener(ServletContext servletContext) {
    // super.registerContextLoaderListener(servletContext);
    // }
    // @Override
    // public void onStartup(ServletContext container) {
    // // Create the 'root' Spring application context
    // AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    // rootContext.register(SpringConfiguration.class);
    // rootContext.setConfigLocation("/WEB-INF/classes/aws-config.xml");
    //
    // // Manage the lifecycle of the root application context
    // container.addListener(new ContextLoaderListener(rootContext));
    //
    // // Add request/session scope listener
    // container.addListener(RequestContextListener.class);
    //
    // // Create the dispatcher servlet's Spring application context
    // AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
    // dispatcherContext.register(SpringConfiguration.class);
    //
    // // Register and map the dispatcher servlet
    // ServletRegistration.Dynamic dispatcher =
    // container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
    // dispatcher.setLoadOnStartup(1);
    // dispatcher.addMapping("/");
    // }
}