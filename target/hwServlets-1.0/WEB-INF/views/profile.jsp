<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/_header.jsp" %>

<%@taglib tagdir="/WEB-INF/tags" prefix="t" %>


<!-- <h2>Hello, . This is the private page!</h2> !-->

<div class="container-xl px-4 mt-4">


    <t:profile_page_header isAdmin="${not empty admin}"></t:profile_page_header>
    <hr class="mt-0 mb-4">
    <div class="row">
        <div class="col-xl-8">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header">Account Details</div>
                <div class="card-body">
                    <form method="POST">

                        <!-- Form Row-->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (first name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="firstName">First name</label>
                                <input class="form-control" name="firstName" id="firstName" type="text" placeholder="Enter your first name" value="${USER.firstName}">
                            </div>
                            <!-- Form Group (last name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="lastName">Last name</label>
                                <input class="form-control" name="lastName"id="lastName" type="text" placeholder="Enter your last name" value="${USER.lastName}">
                            </div>
                        </div>
                        <!-- Form Group (email address)-->
                        <div class="mb-3">
                            <label class="small mb-1" for="email">Email</label>
                            <input class="form-control" name="email" id="email" type="email" placeholder="Enter your email address" value="${USER.email}">
                        </div>
                        <!-- Form Row-->
                        <div class="row gx-4 mb-3">
                            <div class="col-md-4">
                                <label class="small mb-1">Country</label>
                                <t:select_countries></t:select_countries>
                            </div>
                            <!-- Form Group (birthday)-->
                            <div class="col-md-4">
                                <label class="small mb-1" for="date-of-birth">Date of birth</label>
                                <input class="form-control" name="date-of-birth" id="date-of-birth" type="date" name="birthday" placeholder="Enter your birthday" value="${USER.dateOfBirth}">
                            </div>

                            <div class="col-md-4">
                                <label class="small mb-1" for="date-of-birth">Gender</label>
                                <t:select_gender></t:select_gender>
                            </div>
                        </div>
                        <!-- Save changes button-->
                        <input  type="submit" class="btn btn-primary"  href=""  value="Save changes" placeholder="Save changes" >
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/_footer.jsp" %>

