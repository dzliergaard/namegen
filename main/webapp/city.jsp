<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html ng-app="CityGen">
<jsp:include page="/header.jsp"/>
<script type="text/javascript" src="js/services/city-store.js"></script>
<script type="text/javascript" src="js/resources/city-calls.js"></script>
<script type="text/javascript" src="js/controllers/city.js"></script>
<script type="text/javascript" src="js/directives/city-form.js"></script>
<script type="text/javascript" src="js/directives/city-table.js"></script>
<title>DZL City Gen</title>
<script type="text/javascript">
    var cityGen = angular.module("CityGen", ['dzl.controllers', 'dzl.directives']);

    cityGen.config(function ($httpProvider) {
        $httpProvider.defaults.headers.common['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
    });
</script>
<body ng-controller="CityCtrl">
<div class="content row">
    <city-form city-form="cityForm" state="state" generate="generate"></city-form>
</div>
<div class="content row">
    <city-table city="city" get-new="getNew"></city-table>
</div>
</body>
</html>