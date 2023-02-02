<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "com.springlab.member.MemberDTO" %>
    
    <% 
    	MemberDTO member = (MemberDTO) session.getAttribute("member");
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 상세 정보</title>
</head>
<body>
<center>
	<form action="getMember.do" method="post">
		<input type="hidden" name="idx" value="<%= member.getIdx() %>">
	<table border="1" cellspacing="0" cellpadding="0">
		<tr>
			<td bgcolor="yellow" width="70"> ID </td>
			<td><input type="text" name="id" value="<%= member.getId() %>"></td>
		</tr>
	
		<tr>
			<td bgcolor="yellow" width="70"> 비밀번호 </td>
			<td><%= member.getPass() %></td>
		</tr>
		
		<tr>
			<td bgcolor="yellow" width="70"> 이름 </td>
			<td><%= member.getName() %></td>
		</tr>
		
		<tr>
			<td bgcolor="yellow" width="70"> 이메일 </td>
			<td><%= member.getEmail() %></td>
		</tr>
		
		<tr>
			<td bgcolor="yellow" width="70"> 나이 </td>
			<td><%= member.getAge() %></td>
		</tr>
		
		<tr>
			<td bgcolor="yellow" width="70"> 몸무게 </td>
			<td><%= member.getWeight() %></td>
		</tr>
		
		<tr>
			<td bgcolor="yellow" width="70"> 회원가입 날짜 </td>
			<td><%= member.getRegdate() %></td>
		</tr>
		
		<tr>
			<td bgcolor="yellow" width="70"> 로그인 횟수 </td>
			<td><%= member.getCnt() %></td>
		</tr>
	</table>
	</form>
	
	<p>
	<a href="insertMember.jsp"> 회원가입 </a>
	<a href="getMemberList.jsp"> 회원 목록 </a>
	

</center>

</body>
</html>