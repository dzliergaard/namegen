<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html ng-app="CityGen">
<jsp:include page="/header.jsp"/>
<title>DZL City Gen</title>

<body class="content-city">
<script type="text/javascript">
    System.import('./../js/city/city-app');
</script>
<div city-app class="content"
     species-values='${speciesValues}'
     diversity-values='${diversityValues}'
     size-values='${sizeValues}'></div>
</body>
</html>