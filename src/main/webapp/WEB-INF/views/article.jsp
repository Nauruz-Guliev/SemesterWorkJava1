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
                <input onclick="addComment( ${post.id}, document.getElementById('comment').value)" type="submit"
                       value="Add"
                       class="btn btn-dark">
            </div>
        </div>

    </c:if>

    <c:if test="${empty user}">
        <div class="card-body p-2 m-3">
            <p>In order to comment you need to <a href="<c:url value="/signin"/>">sign in</a>. </p>
        </div>
    </c:if>


    <c:forEach var="entry" items="${commentAuthors}"  >
        <div id="commentDiv" class="commentDiv">
            <div class="card mb-3">
                <div class="card-body">
                    <div class="d-flex flex-start">
                        <div class="w-100">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h6 class="text-primary fw-bold mb-0">
                                        ${entry.value.firstName} ${entry.value.lastName}
                                </h6>
                                <p class="mb-0">${entry.key.created_at}</p>
                            </div>
                            <span class="text-dark ms-2 m-2">${entry.key.text}</span>
                            <div class="d-flex justify-content-between align-items-center">
                                <p class="small mb-0" style="color: #aaa;"></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div></div>
        </div>
    </c:forEach>

</div>
