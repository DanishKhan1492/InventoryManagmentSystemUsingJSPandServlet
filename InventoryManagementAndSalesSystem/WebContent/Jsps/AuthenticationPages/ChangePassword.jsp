<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>
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
			<form action="/InventoryManagementAndSalesSystem/changePassword" method="post">
				<table>
					<tr>
						<td class="label"><label for="oldPassword">Old Password</label></td>
						<td><input type="text"  class="input" name="oldPassword" id="oldPassword" placeholder="Enter Old Password" ></td>
						<td class="label"><label for="oldPassword" style=" color:red;">${validate.errorOldPassword}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="newPassword">New Password</label></td>
						<td><input type="password" class="input" name="newPassword" id="newPassword" placeholder="Enter New Password" ></td>
						<td class="label"><label for="newPassword" style="color:red;">${validate.errorNewPassword}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="confirmPassword">Confirm Password</label></td>
						<td><input type="password" class="input" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" ></td>
						<td class="label"><label for="confirmPassword" style="color:red;">${validate.errorConfirmPassword}</label></td>
					</tr>
					<tr>
						<td><a href="/InventoryManagementAndSalesSystem/loginPage" class="button">Goto Login Page</a></td>
						<td><input type="submit" value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>