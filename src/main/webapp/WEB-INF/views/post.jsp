<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/WEB-INF/views/_header.jsp" %>

<script src="${pageContext.request.contextPath}/js/like.js"></script>
<script src="${pageContext.request.contextPath}/js/comment.js"></script>


<div id="article-div" class="card m-4">

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
            <c:if test="${not empty postTags}">
                <c:forEach var="tag" items="${postTags}">
                    <li><a class="link-primary"
                    href="<c:url value="/main?tagId=${tag.tag_name_id}"/>"
                    >${tag.name}</a></li>
                </c:forEach>
            </c:if>


            <c:if test="${not empty email}">
                <button id="likeIcon" onclick="addLike(${post.id})" type="submit">
                    <c:if test="${!isLiked || empty isLiked}">
                        <i class="bi bi-hand-thumbs-up p-2"></i>
                    </c:if>
                    <c:if test="${isLiked}">
                        <i class="bi bi-hand-thumbs-up-fill p-2"></i>
                    </c:if>

                        ${likeCount}

                </button>
            </c:if>


        </c:if>


    </div>
    <c:if test="${not empty user}">

        <div class="input-group p-4 pb-0">
            <input id="comment" name="comment" type="text" class="form-control" placeholder="Comment"
                   aria-label="Comment"
                   aria-describedby="basic-addon2">
            <div class="input-group-append">
                <input id="btnAddComm" type="submit"
                       value="Add"
                       class="btn btn-dark">
            </div>
        </div>

        <div id="postIdDiv">${post.id}</div>

    </c:if>


    <c:if test="${empty user}">
        <div class="card-body p-2 m-3">
            <p>In order to comment you need to <a href="<c:url value="/signin"/>">sign in</a>. </p>
        </div>
    </c:if>

    <div id="commentDiv" class="commentDiv">
        <c:forEach var="entry" items="${commentAuthors}">

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

        </c:forEach>
    </div>


    <nav>
        <ul class="pagination m-4">
            <c:forEach begin="0" end="${commentCount}" var="i" step="10">

                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/post?postIndex=${post.id}&offset=${i}">
                    <fmt:formatNumber value="${(i/10)+1}" minFractionDigits="0" maxFractionDigits="0"/>

                </a></li>

            </c:forEach>

        </ul>
    </nav>


    <!-- this is invisible by default. Used  for js so that it has something to copy. !-->
    <div id="commentDivInvisible" class="commentDivInvisible">
        <div class="card mb-3">
            <div class="card-body">
                <div class="d-flex flex-start">
                    <div class="w-100">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h6 class="text-primary fw-bold mb-0">

                            </h6>
                            <p class="mb-0"></p>
                        </div>
                        <span class="text-dark ms-2 m-2">$</span>
                        <div class="d-flex justify-content-between align-items-center">
                            <p class="small mb-0"></p>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>


</div>

<%@include file="/WEB-INF/views/_footer.jsp" %>

