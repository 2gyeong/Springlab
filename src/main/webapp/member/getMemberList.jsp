<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import ="java.util.List" %>
<%@ page import ="com.springlab.member.MemberDTO" %>

<%
	List<MemberDTO> memberList = (List) session.getAttribute("memberList");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 리스트</title>
</head>
<body>

<h1> 회원 목록 페이지</h1>
<hr>

<form action="getmemberList.jsp" method="post">
	<table border="1" cellspacing="0" cellpadding ="0" width="700px">
		<tr>
			<th bgcolor="yellow" width="100px">번호</th>
			<th bgcolor="yellow" width="100px">아이디</th>
			<th bgcolor="yellow" width="100px">비밀번호</th>
			<th bgcolor="yellow" width="100px">이름</th>
			<th bgcolor="yellow" width="100px">이메일</th>
			<th bgcolor="yellow" width="100px">나이</th>
			<th bgcolor="yellow" width="100px">몸무게</th>
			<th bgcolor="yellow" width="100px">등록일</th>
			<th bgcolor="yellow" width="100px">로그인 횟수</th>
		</tr>
		
		<% for(MemberDTO dto : memberList){ %>
			<tr>
				<td><%= dto.getIdx() %></td>
				<td><a href="getMember.do?idx=<%= dto.getIdx() %>">
				<%= dto.getId() %></a></td>
				<td><%= dto.getPass() %></td>
				<td><%= dto.getName() %></td>
				<td><%= dto.getEmail()%></td>
				<td><%= dto.getAge()%></td>
				<td><%= dto.getWeight()%></td>
				<td><%= dto.getRegdate() %></td>
				<td><%= dto.getCnt() %></td>
				
			</tr>
		<% } %>
	</table>
	
	<p/>
	<a href="login.jsp"> 로그인 </a> &nbsp;&nbsp;&nbsp;
	<a href="insertMember.jsp"> 회원가입 </a>

</form>

</body>
</html>