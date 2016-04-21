<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  ~  RPToolkit - Tools to assist Role-Playing Game masters and players
  ~  Copyright (C) 2016  Dane Zeke Liergaard
  ~
  ~  This program is free software: you can redistribute it and/or modify
  ~  it under the terms of the GNU General Public License as published by
  ~  the Free Software Foundation, either version 3 of the License, or
  ~  (at your option) any later version.
  ~
  ~  This program is distributed in the hope that it will be useful,
  ~  but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  GNU General Public License for more details.
  ~
  ~  You should have received a copy of the GNU General Public License
  ~  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  --%>

<!DOCTYPE html>
<html>
<head>
    <title>RP Toolkit</title>
    <base href="/">
    <!-- Angular2 and its dependencies -->
    <spring:url value="/js/node_modules/es6-shim/es6-shim.js" var="es6Shim"/>
    <spring:url value="/js/node_modules/systemjs/dist/system-polyfills.js" var="sysPolyfills"/>
    <spring:url value="/js/node_modules/angular2/bundles/angular2-polyfills.js" var="angPolyfills"/>
    <spring:url value="/js/node_modules/systemjs/dist/system.src.js" var="systemJs"/>
    <spring:url value="/js/node_modules/rxjs/bundles/Rx.js" var="rxJs"/>
    <spring:url value="/js/node_modules/angular2/bundles/angular2.dev.js" var="angularDev"/>
    <spring:url value="/js/node_modules/angular2/bundles/router.dev.js" var="angularRoute"/>
    <spring:url value="/js/node_modules/angular2/bundles/http.dev.js" var="angularHttp"/>

    <!-- material design from google -->
    <spring:url value="/js/node_modules/material-design-lite/material.min.js" var="matJs"/>
    <spring:url value="https://code.getmdl.io/1.1.3/material.purple-orange.min.css" var="matCss"/>
    <spring:url value="https://fonts.googleapis.com/icon?family=Material+Icons" var="matIcon"/>

    <spring:url value="/css/main.css" var="mainCss"/>

    <script src="${sysPolyfills}"></script>
    <script src="${es6Shim}"></script>
    <script src="${systemJs}"></script>
    <script src="${angPolyfills}"></script>
    <script src="${angularDev}"></script>
    <script src="${angularRoute}"></script>
    <script src="${rxJs}"></script>
    <script src="${angularHttp}"></script>
    <script src="${matJs}"></script>

    <link rel="stylesheet" href="${mainCss}"/>
    <link rel="stylesheet" href="${matCss}">
    <link rel="stylesheet" href="${matIcon}">
    <%--<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">--%>

    <meta id="training-name" content='${trainingName}'>
    <meta id="name-attributes" content='${nameAttributes}'>

    <resource href="/loading.gif" height="40" width="40">
        <meta http-equiv="Content-type" content="image/gif">
    </resource>
</head>

<script type="text/javascript">
    System.config({
        paths: {
            'app/': '/js/app/',
            'city/': '/js/city/',
            'name/': '/js/name/',
            'util/': '/js/util/',
            'underscore': '/js/node_modules/underscore/underscore-min.js'
        },
        packages: {
            app: {
                formater: 'register',
                defaultExtension: 'js'
            },
            city: {
                formater: 'register',
                defaultExtension: 'js'
            },
            name: {
                formater: 'register',
                defaultExtension: 'js'
            },
            util: {
                formater: 'register',
                defaultExtension: 'js'
            }
        },
        main: "main",
        meta: {}
    });

    System.import('/js/app/main');
</script>

<body class="content">
<input id="user-authed" style="display:none" value="${userAuthed}">
<rptools-app></rptools-app>
</body>
</html>