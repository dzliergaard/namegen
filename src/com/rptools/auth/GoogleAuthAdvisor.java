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

package com.rptools.auth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.http.GenericUrl;
import com.google.gson.Gson;
import com.rptools.annotation.RequiresGoogleAuth;

/**
 * Spring {@link Aspect} that intercepts requests that require authentication, and
 * forwards the page to the Google authorization URL if necessary
 */
@Aspect
@Component
public class GoogleAuthAdvisor {
    private static final String PARAMETERS_REQUIRED_EXCEPTION =
            "Request and response parameters are required for @RequiresGoogleAuth methods";

    private final Gson gson;
    private final UserCredentials userCredentials;

    @Autowired
    public GoogleAuthAdvisor(Gson gson, UserCredentials userCredentials) {
        this.gson = gson;
        this.userCredentials = userCredentials;
    }

    @Pointcut("@annotation(requiresGoogleAuth)")
    void requiresGoogleAuth(RequiresGoogleAuth requiresGoogleAuth) {
    }

    /**
     * Checks around @{@link RequiresGoogleAuth} annotated REST methods that confirms that a user is signed in, or
     * forwards them to the authorization link if not.
     */
    @Around("requiresGoogleAuth(annotation)")
    public Object checkAccessCode(ProceedingJoinPoint joinPoint, RequiresGoogleAuth annotation) throws Throwable {
        if (userCredentials.getCredential().isPresent()) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = getJoinPointArg(joinPoint.getArgs(), HttpServletRequest.class);
        HttpServletResponse response = getJoinPointArg(joinPoint.getArgs(), HttpServletResponse.class);
        if (request != null && response != null) {
            redirectToGoogleAuth(request, response);
        } else if (response != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            throw new IllegalArgumentException(PARAMETERS_REQUIRED_EXCEPTION);
        }
        return null;
    }

    private void redirectToGoogleAuth(HttpServletRequest request, HttpServletResponse response)
            throws IOException, URISyntaxException {
        GenericUrl requestURL = new GenericUrl(request.getRequestURL().toString());
        requestURL.setRawPath("/googleAuth");
        Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
        parameterMap.put("returnUri", new String[] { request.getRequestURI() });

        response.sendRedirect(
            new URIBuilder(requestURL.build()).addParameter("state", gson.toJson(parameterMap)).build().toString());
    }

    @SuppressWarnings("unchecked")
    private <T> T getJoinPointArg(Object[] args, Class<T> clazz) {
        for (Object ob : args) {
            if (clazz.isAssignableFrom(ob.getClass())) {
                return (T) ob;
            }
        }
        return null;
    }
}
