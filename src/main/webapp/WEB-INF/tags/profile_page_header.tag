<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="profile_header" pageEncoding="UTF-8" %>
<%@attribute name="isAdmin" required="true" type="java.lang.Boolean" %>

<jsp:doBody/>
<nav class="nav nav-borders">
    <a class="nav-link active ms-0" href="<c:url value="/profile/general"></c:url>"> Profile</a>
    <a class="nav-link active ms-0" href="<c:url value="/profile/security"></c:url>"> Security</a>

    <c:if test="${isAdmin}">
        <a class="nav-link active ms-0" href="<c:url value="/admin"/>"> Admin</a>
    </c:if>
</nav>
<jsp:doBody/>

