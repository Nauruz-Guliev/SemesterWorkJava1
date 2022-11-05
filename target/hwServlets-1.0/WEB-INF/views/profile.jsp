<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/_header.jsp" %>

<h2>Hello, ${user.get("email")}. This is the private page!</h2>
<%@include file="/WEB-INF/views/_footer.jsp" %>

