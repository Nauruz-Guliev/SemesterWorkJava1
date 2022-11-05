<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:forEach begin="0" end="${fn:length(postsList) - 1}" var="i">
    <div class="container-fluid table-bordered">
        <div class="row">

            <div class="col-8">
                <div class="card-body" style="transform: rotate(0);">
                    <div class="card bg-light mb-3 p-4 m-2" >
                        <h5 class="card-title">${postsList[i].title}</h5>
                        <p class="card-text"> ${postsList[i].body}</p>
                    </div>
                    <a href="${pageContext.request.contextPath}/article?postIndex=${postsList[i].id}" class="stretched-link"></a>
                </div>
            </div>

            <div class="col-4">
                <div class="card-body">
                    <div class="card bg-light mb-3 p-4 m-2">
                        <h5 class="card-title">VLALBASDADASD</h5>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<%@include file="/WEB-INF/views/_footer.jsp" %>


