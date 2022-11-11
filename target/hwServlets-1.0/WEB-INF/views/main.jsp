<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div class="container-fluid table-bordered">

    <div class="row">
        <c:if test="${not empty postsList}">
            <c:forEach begin="0" end="${fn:length(postsList) - 1}" var="i">
                <div class="col-8">
                    <div class="card-body">
                        <div class="card bg-light mb-3 p-4 m-2">
                            <h5 class="card-title">${postsList[i].title}</h5>
                            <p class="card-text"> ${postsList[i].body}</p>

                            <a href="${pageContext.request.contextPath}/post?postIndex=${postsList[i].id}"
                               class="stretched-link"></a>
                        </div>

                    </div>
                </div>
                <c:if test="${i==0}">
                    <div class="col-4 h-100">
                        <div class="card-body">
                            <div class="card bg-light mb-3 p-4 m-2">
                                <h3 class="card-title">Most popular</h3>
                                <c:if test="${not empty mostPopular}">
                                    <c:forEach begin="0" end="${fn:length(mostPopular) - 1}" var="i">
                                        <a class="nav-link"
                                           href="${pageContext.request.contextPath}/post?postIndex=${mostPopular[i].id}">
                                                ${mostPopular[i].title}
                                        </a>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </div>

                </c:if>
            </c:forEach>
        </c:if>

    </div>
</div>

<%@include file="/WEB-INF/views/_footer.jsp" %>


