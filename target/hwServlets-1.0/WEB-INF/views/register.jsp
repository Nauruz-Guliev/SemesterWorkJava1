<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/views/_header.jsp" %>

<%@taglib tagdir="/WEB-INF/tags" prefix="t" %>


<div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card shadow-2-strong" style="border-radius: 1rem;">
                <div class="card-body p-5 text-center">

                    <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>


                    <form class="mx-1 mx-md-4" action="<c:url value="register"/>" method="POST">

                        <div class="form-floating mb-3">
                            <input class="form-control" name="firstName" id="firstName" type="text"
                                   placeholder="First name">
                            <label for="firstName">First Name</label>
                        </div>


                        <div class="form-floating mb-3">
                            <input class="form-control" name="lastName" id="lastName" type="text"
                                   placeholder="Last Name">
                            <label for="lastName">Last Name</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input class="form-control" id="email" name="email" type="email" placeholder="E-MAIL"
                            <c:if test="${not empty email}"> value="<c:out value="${email}"/>"
                            </c:if>>
                            <label for="email">Email</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input class="form-control" name="password" type="password" id="password"
                                   placeholder="Password">
                            <label for="password">Password</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input class="form-control" name="password-confirm" type="password"
                                   id="password-confirm" placeholder="Confirm password">
                            <label for="password-confirm">Confirm password</label>
                        </div>


                       <t:select_countries></t:select_countries>


                        <select class="form-select mb-3" name="gender">
                            <option value="">-Select Gender-</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>


                        <input class="form-control" type="date" name="date-of-birth" placeholder="Date of birth">
                        <br>

                        <div class="form-check">
                            <input class="form-check-input" id="flexCheckChecked" type="checkbox"
                                   name="policy-agreement" value="agree"/>
                            <label class="form-check-label" for="flexCheckChecked">
                                Policy agreement
                            </label>
                        </div>
                        <br>
                        <input type="submit" value="CREATE ACCOUNT" class="btn btn-primary btn-lg btn-block">
                        <div class="div-messages">
                            <c:forEach begin="0" end="${fn:length(errorList) - 1}" var="index">
                                <div class="div-error-message"><c:out value="${errorList[index]}"/></
                                >
                            </c:forEach>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<%@include file="/WEB-INF/views/_footer.jsp" %>
