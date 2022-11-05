<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Servlets home work</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="main.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="mynavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/>" class="button">Main page</a>
                </li>

                <c:if test="${empty user}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/signin"/>" class="button">Sign In</a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/register"/>"
                                            class="button">Register</a></li>
                </c:if>


                <c:if test="${not empty user}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/profile"/>" class="button">Profile</a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/signout"/>" class="button">Sign
                        Out</a></li>
                </c:if>
                <c:if test="${not empty admin}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/admin"/>" class="button">Admin</a>
                    </li>
                </c:if>


            </ul>
        </div>
    </div>
</nav>

<section style="background-color: #FFFDED; height: 100%">
