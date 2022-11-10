<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Servlets home work</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
</head>
<body>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="mynavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/>">Main page</a>
                </li>

                <c:if test="${empty user}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/signin"/>">Sign In</a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/register"/>"
                                            class="button">Register</a></li>
                </c:if>


                <c:if test="${not empty user}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/profile/general"/>">Profile</a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/signout"/>">Sign
                        Out</a></li>
                </c:if>
                <c:if test="${not empty admin}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/admin"/>">Admin</a>
                    </li>
                </c:if>


            </ul>
        </div>
    </div>
</nav>

<section style="background-color: #FFFDED; height: 100%">
    <div class="modal" tabindex="-1" id="modal">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 id="modal-title" class="modal-title"></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p id="message-body">Modal body text goes here.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

