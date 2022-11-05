<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/views/_header.jsp" %>

<div class="card m-4">
  <div class="card-body m-2">
    <c:if test="${not empty post}">
      <h5 class="card-title p-4">
        <c:out value="${post.title}"/>
      </h5>

      <p class="card-text p-4">
        <c:out value="${post.body}"/>
      </p>
    </c:if>
  </div>
</div>
