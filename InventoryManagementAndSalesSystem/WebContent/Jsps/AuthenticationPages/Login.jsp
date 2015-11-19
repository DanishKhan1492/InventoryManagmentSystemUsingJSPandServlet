<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LOG IN</title>
<link rel="stylesheet" type="text\css" href="resources/Css/Login.css"> 
</head>
<body>
	<div id="container">
		<div id="messageContainer" style="color:#8e44ad; font-size:20px;">
			<c:if test="${msg != null}">
				<span class="message">${msg}</span>
			</c:if>
		</div>
		
		<div id="formContainer">
			<form action="/InventoryManagementAndSalesSystem/login" method="post">
				<table>
					<tr>
						<td class="label"><label for="userName" class="label">User Name</label></td>
						<td><input type="text" name="userName" class="input" id="userName" placeholder="Enter User Name" value="${validate.userName}"></td>
						<td class="label"><label for="userName" class="label" style="color:red;">${validate.errorUserName}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="password" class="label">Password</label></td>
						<td><input type="password" class="input" name="password" id="password" placeholder="Enter Password" value="${validate.userName}" ></td>
						<td class="label"><label for="password" class="label" style="color:red;">${validate.errorPassword}</label></td>
					</tr>
					<tr>
						<td><a href="/InventoryManagementAndSalesSystem/changePasswordPage" class="button">Change Password</a></td>
						<td><input type="submit" value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>