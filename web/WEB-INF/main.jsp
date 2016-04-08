<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>RP Toolkit</title>
    <base href="/">
    <spring:url value="/js/node_modules/es6-shim/es6-shim.js" var="es6Shim"/>
    <spring:url value="/js/node_modules/systemjs/dist/system-polyfills.js" var="sysPolyfills"/>
    <spring:url value="/js/node_modules/angular2/bundles/angular2-polyfills.js" var="angPolyfills"/>
    <spring:url value="/js/node_modules/systemjs/dist/system.src.js" var="systemJs"/>
    <spring:url value="/js/node_modules/rxjs/bundles/Rx.js" var="rxJs"/>
    <spring:url value="/js/node_modules/angular2/bundles/angular2.dev.js" var="angularDev"/>
    <spring:url value="/js/node_modules/angular2/bundles/router.dev.js" var="angularRoute"/>
    <spring:url value="/js/node_modules/angular2/bundles/http.dev.js" var="angularHttp"/>

    <spring:url value="/resources/bootstrap/dist/css/bootstrap.css" var="bootCss"/>
    <spring:url value="/css/main.css" var="mainCss"/>

    <script src="${sysPolyfills}"></script>
    <script src="${es6Shim}"></script>
    <script src="${systemJs}"></script>
    <script src="${angPolyfills}"></script>
    <script src="${angularDev}"></script>
    <script src="${angularRoute}"></script>
    <script src="${rxJs}"></script>
    <script src="${angularHttp}"></script>

    <link rel="stylesheet" href="${bootCss}"/>
    <link rel="stylesheet" href="${mainCss}"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">

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
            // 3rd party libraries
            'jquery': '/js/node_modules/jquery/dist/jquery.min.js',
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
//            js: {
//                formater: 'register',
//                defaultExtension: 'js'
//            },
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
<input id="user-authed" class="hide" value="${userAuthed}">
<rptools-app></rptools-app>
</body>
</html>