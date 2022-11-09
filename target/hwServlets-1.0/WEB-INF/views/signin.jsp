<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>

<script type="text/javascript">
    const showPopup = () => {
        let myModal = new bootstrap.Modal(document.getElementById("modal"), {});
        myModal.show();
    }
</script>


<div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card shadow-2-strong" style="border-radius: 1rem;">
                <div class="card-body p-5 text-center">

                    <h3 class="mb-5">Sign in</h3>

                    <form action="<c:url value="/signin"/>" method="POST">
                        <div class="form-floating mb-3">
                            <input name="email" type="text" class="form-control" autofocus id="email"
                                   placeholder="Email"
                            <c:if test="${not empty email}"> value="<c:out value="${email}"/>"
                            </c:if>
                            >
                            <label for="email">Login</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input class="form-control" name="password" id="password" type="password"
                                   placeholder="Password">
                            <label for="password">Password</label>
                        </div>

                        <br>
                        <input type="submit" value="Sing In" class="btn btn-primary btn-lg btn-block"
                               onclick="showPopup()"
                        >
                        <c:if test="${not empty errorMessage}">
                            <div class="div-error-message">
                                <c:out value="${errorMessage}"/>
                            </div>
                        </c:if>
                    </form>

                    <div class="modal" tabindex="-1" id="modal">
                        <div class="modal-dialog">
                            <div class="modal-content">

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>


<%@include file="/WEB-INF/views/_footer.jsp" %>
