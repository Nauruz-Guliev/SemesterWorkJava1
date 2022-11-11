<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/WEB-INF/views/_header.jsp" %>


<script src="${pageContext.request.contextPath}/js/tag.js"></script>



<div id="article-div" class="card m-4">
    <form action="<c:url value="/post/create"/>" method="POST">
        <div id="create-article" class="card-body m-2">
            <h1 class="h1">Create an article</h1>
            <div class="mb-3">
                <label for="titleControl" class="form-label">Article title</label>
                <input type="text" class="form-control" id="titleControl" name="title">
            </div>
            <div class="mb-3">
                <label for="textBodyControl" class="form-label">Example textarea</label>
                <textarea class="form-control" id="textBodyControl" rows="6" name="body"></textarea>
            </div>

            <div id="tag-field" class="col-sm-6">
                <label class="small mb-1" for="tag">Tag</label>
                <input class="form-control" name="tag" id="tag" type="text"
                       value="">
            </div>


        </div>

        <button id="btn-add-tag" type="button" class="btn btn-primary btn-lg btn-floating m-4 p-0">
            <i class="bi-plus-circle-fill"></i>
        </button>

        <input type="submit" value="Create" class="btn btn-primary btn-lg btn-block m-4">


    </form>
</div>


<%@include file="/WEB-INF/views/_footer.jsp" %>
