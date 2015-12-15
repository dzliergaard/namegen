<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<!-- page essentials like jquery, bootstrap, angular -->
<!DOCTYPE html>
<head>
    <script src="js/node_modules/es6-shim/es6-shim.js"></script>
    <script src="js/node_modules/systemjs/dist/system.src.js"></script>
    <script src="js/node_modules/angular2/bundles/angular2.js"></script>
    <script src="js/node_modules/angular2/bundles/http.js"></script>
    <script type="text/javascript" src="js/arrayExpansion.js"></script>

    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="css/bootstrap.css.map"/>
    <link rel="stylesheet" href="css/bootstrap-theme.css">
    <link rel="stylesheet" href="css/bootstrap-theme.css.map">
    <link rel="stylesheet" href="css/main.css"/>
</head>
<resource href="resources/loading.gif" height="40" width="40">
    <meta http-equiv="Content-type" content="image/gif">
</resource>
<nav class="navbar" role="navigation">
    <div>
        <div class="navbar-header margin-left-10">
            <a class="navbar-brand">DZ Liergaard</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a id="nameGen" href="name" target="_self">Name Gen</a></li>
                <li><a id="cityGen" href="city" target="_self">City Gen</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <%
                    UserService userService = UserServiceFactory.getUserService();
                    User user = userService.getCurrentUser();
                    String logURL, listItemClass;
                    boolean signin;
                    if (user == null) {
                        logURL = userService.createLoginURL(request.getRequestURI().replace(".jsp", ""));
                        listItemClass = "signin";
                        signin = true;
                    } else {
                        logURL = userService.createLogoutURL(request.getRequestURI().replace(".jsp", ""));
                        listItemClass = "signout";
                        signin = false;
                    }
                %>
                <li class="<%= listItemClass %>">
                    <a href="<%= logURL %>">
                        <%= signin ? "sign in" : "sign out" %>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script type="text/javascript">
    System.config({
        packages: {'js': {defaultExtension: 'js'}},
        main: "main",
        map: {
            'underscore': 'http://underscorejs.org/underscore-min.js',
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"
        },
        meta: {
            'angular2/angular2': {
                format: 'global',
                exports: 'angular',
                deps: [
                    'jquery',
                    'underscore'
                ]
            }
        }
    });
</script>