<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, userjsp.*" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%
String pg = request.getParameter("pg");
int currentPage = (pg == null) ? 1 : Integer.parseInt(pg);
int pageSize = 15;
List<User> list = UserDAO.findAll(currentPage, pageSize);
int recordCount = UserDAO.count();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
<script src="/userjsp/res/common.js"></script>
<link rel="stylesheet" href="/userjsp/res/common.css"/>
<style>
  div.container { width: 600px; }
  h1 { margin-bottom: -20px; }
  a.btn { float: right; margin-bottom: 5px; }
</style>
</head>
<body>
<div class="container">

<h1>사용자 목록</h1>
<a class="btn" href="userCreate.jsp?pg=<%= currentPage %>">사용자등록</a>

<table class="table">
  <thead>
    <tr>
      <th>사용자ID</th>
      <th>이름</th>
      <th>이메일</th>
      <th>학과</th>
      <th>활성화</th>
      <th>유형</th>
    </tr>
  </thead>
  <tbody>
    <% for (User user : list) { %>
      <tr class="clickable" data-url="userEdit.jsp?id=<%= user.getId() %>&pg=<%= currentPage %>">
        <td><%= user.getUserid() %></td>
        <td><%= user.getName() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getDepartmentName() %></td>
        <td><%= user.isEnabled() %></td>
        <td><%= user.getUserType() %></td>
      </tr>
    <% } %>
  </tbody>
</table>

<my:pagination pageSize="<%= pageSize %>" recordCount="<%= recordCount %>" queryStringName="pg" />

</div>
</body>
</html>
