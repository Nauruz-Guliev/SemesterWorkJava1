<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/views/_header.jsp" %>

<div class="card m-4">
    <div class="card-body m-2">
        <c:if test="${not empty post}">
            <h5 class="card-title p-2">
                <c:out value="${post.title}"/>
            </h5>
            <p><small class="text p-2"> Author: <c:out value="${postAuthor.firstName} ${postAuthor.lastName}"/> </small>
            </p>
            <p class="card-text p-2">
                <c:out value="${post.body}"/>
            </p>


            <i class="bi bi-heart-fill p-2"><c:out value="${post.likes}"/></i>

        </c:if>
        <!-- Используем один цикл для двух списков, так как порядок в них соответсвует-->
        <!-- Можно было использовать хэш мап, что было бы правильнее. Если дойдут руки, переделаю.-->


    </div>
    <c:if test="${not empty user}">

        <div class="input-group p-4 pb-0">
            <input id="comment" name="comment" type="text" class="form-control" placeholder="Comment"
                   aria-label="Comment"
                   aria-describedby="basic-addon2">
            <div class="input-group-append">
                <input onclick="addComment( ${post.id}, document.getElementById('comment').value)" type="submit" value="Add"
                       class="btn btn-dark">
            </div>
        </div>

    </c:if>

    <c:if test="${empty user}">
        <div class="card-body p-2 m-3">
            <p>In order to comment you need to <a href="<c:url value="/signin"/>">sign in</a>. </p>
        </div>
    </c:if>


    <c:forEach begin="0" end="${fn:length(commentList) - 1}" var="i">
        <div class="commentDiv" id="commentDiv">

            <div class="card">
                <div class="card-body p-2 m-3">
                    <p>${commentList[i].text}</p>
                    <div class="d-flex justify-content-between p-2">
                        <div class="d-flex flex-row">
                            <p class="small mb-0 ms-2">${commentAuthors[i].firstName} ${commentAuthors[i].lastName}</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </c:forEach>
</div>
