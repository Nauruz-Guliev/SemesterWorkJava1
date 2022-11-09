<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<script type="text/javascript">

</script>

<p class="text-center">Here you can change country of any user.</p>

<form action="<c:url value="admin"/>" method="POST">

    <div class="container">
        <div class="row">
            <div class="col-sm">
                <input type="number" min="0" class="form-control" name="userId" id="userId" type="text">
            </div>
            <div class="col-sm">
                <t:select_countries></t:select_countries>
            </div>
            <div class="col-sm">
                <input id="submit" name="submit" type="submit" value="save" class="btn btn-dark">
            </div>

            <c:if test="${not empty updateErrorMessage}">
                <div class="col-sm">
                    <c:out value="${updateErrorMessage}"/>
                </div>
            </c:if>
        </div>
    </div>

</form>


<table class="table">

    <thead>
    <tr>
        <th scope="col">id</th>
        <th scope="col">First Name</th>
        <th scope="col">Last Name</th>
        <th scope="col">City</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>


    <c:forEach begin="0" end="${fn:length(usersList) - 1}" var="i">

        <tr>
            <th scope="row">${usersList[i].id}</th>

            <td>
                <div class="firstName"><c:out value="${usersList[i].firstName}"/></
                >
            </td>
            <td>
                <div class="lastName"><c:out value="${usersList[i].lastName}"/></
                >
            </td>
            <td>
                <div class="country"><c:out value="${usersList[i].country}"/></
                >
            </td>
        </tr>
    </c:forEach>


    </tbody>
</table>


<%@include file="/WEB-INF/views/_footer.jsp" %>
