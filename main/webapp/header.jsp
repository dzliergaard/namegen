<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<!-- page essentials like jquery, bootstrap, angular -->
<!DOCTYPE html>
<head>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script src="https://code.angularjs.org/1.2.9/angular.min.js"></script>
    <script src="https://code.angularjs.org/1.2.9/angular-resource.min.js"></script>
    <script type="text/javascript" src="js/init.js"></script>
    <script type="text/javascript" src="js/arrayExpansion.js"></script>
    <script type="text/javascript" src="js/ui-bootstrap-tpls-0.12.0.min.js"></script>

    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="css/bootstrap.css.map"/>
    <link rel="stylesheet" href="css/bootstrap-theme.css">
    <link rel="stylesheet" href="css/bootstrap-theme.css.map">
    <link rel="stylesheet" href="css/main.css"/>
</head>
<resource href="resources/loading.gif" height="40" width="40">
    <meta http-equiv="Content-type" content="image/gif">
</resource>
<nav ng-app="Header" class="navbar" role="navigation">
    <div>
        <div class="navbar-header margin-left-10">
            <a class="navbar-brand">DZ Liergaard</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown">
                        RP Tools
                    </a>
                    <ul class="dropdown-menu">
                        <li><a id="nameGen" href="name" target="_self">Name Gen</a></li>
                        <li><a id="cityGen" href="city" target="_self">City Gen</a></li>
                    </ul>
                </li>
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
    angular.module('Header', ['ui.bootstrap']);
</script>