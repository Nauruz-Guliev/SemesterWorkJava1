<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/_header.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="t" %>

<div class="container-xl px-4 mt-4">
  <!-- Account page navigation-->
  <t:profile_page_header isAdmin="${not empty admin}"></t:profile_page_header>

  <hr class="mt-0 mb-4">
  <div class="row">
    <div class="col-lg-8">
      <!-- Change password card-->
      <div class="card mb-4">
        <div class="card-header">Change Password</div>
        <div class="card-body">
          <form method="post">
            <!-- Form Group (current password)-->
            <div class="mb-3">
              <label class="small mb-1" for="currentPassword">Current Password</label>
              <input class="form-control" name="old_password" id="currentPassword" type="password" placeholder="Enter current password">
            </div>
            <!-- Form Group (new password)-->
            <div class="mb-3">
              <label class="small mb-1" for="newPassword">New Password</label>
              <input class="form-control" name="password" id="newPassword" type="password" placeholder="Enter new password">
            </div>
            <!-- Form Group (confirm password)-->
            <div class="mb-3">
              <label class="small mb-1" for="confirmPassword">Confirm Password</label>
              <input class="form-control" name="password-confirm" id="confirmPassword" type="password" placeholder="Confirm new password">
            </div>
            <input type="submit" class="btn btn-primary">
          </form>
        </div>
      </div>

    </div>
    <div class="col-lg-4">


      <!-- Delete account card-->
      <div class="card mb-4">
        <div class="card-header">Delete Account</div>
        <div class="card-body">
          <p>Deleting your account is a permanent action and cannot be undone. If you are sure you want to delete your account, select the button below.</p>
          <button onclick="showPopup()" class="btn btn-danger-soft text-danger" type="button">I understand, delete my account</button>
        </div>
      </div>
    </div>
  </div>
</div>


<%@include file="/WEB-INF/views/_footer.jsp" %>

