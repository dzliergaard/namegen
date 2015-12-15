<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<jsp:include page="header.jsp"/>
<title>DZL Name Gen</title>

<body class="content-name">
<script>
    System.import('./js/name/name-app');
</script>
<div name-app
     class="content"
     saved-names='${savedNames}'
     generated-names='${generatedNames}'
     user-auth="<%= UserServiceFactory.getUserService().getCurrentUser() != null %>"
     training-name='${trainingName}'
     name-attributes='${nameAttributes}'>
</div>
</body>
</html>