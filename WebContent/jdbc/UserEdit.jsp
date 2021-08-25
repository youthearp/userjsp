<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, userjsp.*" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="userjsp.User" />
<jsp:setProperty property="*" name="user" />
<%
String pg = request.getParameter("pg");
int currentPage = (pg == null) ? 1 : Integer.parseInt(pg);
String errorMessage = null;
if (request.getMethod().equals("GET")) {
    user = UserDAO.findById(user.getId());
    pageContext.setAttribute("user", user);    
} else if (request.getMethod().equals("POST")) {
    String cmd = request.getParameter("cmd");
    if ("update".equals(cmd))
        errorMessage = UserService.update(user);
    else if ("delete".equals(cmd))
        errorMessage = UserService.delete(user.getId());
    if (errorMessage == null) {
        response.sendRedirect("userList.jsp?pg=" + currentPage);
        return;
    }
}
List<Department> departments = DepartmentDAO.findAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
<script src="/userjsp/res/common.js"></script>
<link rel="stylesheet" href="/userjsp/res/common.css"/>
<script src="https://kit.fontawesome.com/68abb170e0.js" crossorigin="anonymous"></script>
<style>
  form { padding: 10px 40px 40px 40px; width: 400px; }
  label { display: inline-block; width: 60px; text-align: right; margin-right: 4px; }
  form div { margin-top: 20px; }
  input[name=email] { width: 200px; }
</style>
</head>
<body>
<div class="container">

<form method="post" class="shadow">
  <h1>사용자 수정</h1>
  <div>
    <label>사용자ID</label>
    <input type="text" name="userid" value="${ user.userid }" required />
  </div>
  <div>
    <label>이름</label>
    <input type="text" name="name" value="${ user.name }" required />
  </div>
  <div>
    <label>이메일</label>
    <input type="email" name="email" value="${ user.email }" />
  </div>
  <div>
    <label>학과</label>
    <select name="departmentId" required>
      <option value="">소속학과를 선택하세요</option>
      <% for (Department department : departments) { %>
        <% int id = department.getId(); %>
        <% String dname = department.getDepartmentName(); %>
        <option value="<%= id %>" <%= id == user.getDepartmentId() ? "selected" : "" %>>
          <%= dname %>
        </option>
      <% } %>
    </select>
  </div>
  <div>
    <label>활성화</label>
    <input type="checkbox" name="enabled" value="true" ${ user.enabled ? "checked" : "" } /> 
  </div>
  <div>
    <label>유형</label>
    <select name="userType" required>
      <option value="">사용자 유형을 선택하세요</option>
      <option value="학생" ${ user.userType == "학생" ? "selected" : "" }>학생</option>      
      <option value="교수" ${ user.userType == "교수" ? "selected" : "" }>교수</option>
      <option value="관리자" ${ user.userType == "관리자" ? "selected" : "" }>관리자</option>
    </select>
  </div>
  <div>
    <button type="submit" class="btn" name="cmd" value="update">
      <i class="fas fa-check"></i> 저장</button>
    <button type="submit" class="btn" name="cmd" value="delete" data-confirm-delete>
      <i class="fas fa-trash-alt"></i> 삭제</button>    
    <a class="btn" href="userList.jsp?pg=<%= currentPage %>"><i class="fas fa-ban"></i> 취소</a>
  </div>
  <% if (errorMessage != null) { %>
    <div class="error"><%= errorMessage %></div>
  <% } %>
</form>

</div>
</body>
</html>
