<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Borrower</title>
<link rel="stylesheet" href="resources/Css/Login.css" type="text\css">
<link rel="stylesheet" href="resources/Css/AddBorrower.css" type="text\css">
</head>
<body>
	<div id="Container">
		<div id="menu"><jsp:include page="BorrowerMenu.jsp"></jsp:include></div>
		<div id="borrowerContainer">
			<form action="/InventoryManagementAndSalesSystem/payAmount" method="post">
				<table>
					<tr>
						<td class="label"><label for="borrowerCnic">Borrower CNIC</label></td>
						<td><input type="text" name="borrowerCnic" class="input" Placeholder="Enter CNIC" value="${validate.borrowerCnic}"></td>
						<td class="label"><label for="borrowerCnic" style="color: red;">${validate.errorBorrowerCnic}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="payAmount">Amount To Pay</label></td>
						<td><input type="text" name="payAmount" class="input" Placeholder="Enter Amount " ${validate.payAmount}></td>
						<td class="label"><label for="payAmount" style="color: red;">${validate.errorPayAmount}</label></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" Value="Submit" class="button"></td>

					</tr>
					<tr>
						<td></td>
						<td><label class="label" style="color: red;">${msg}</label></td>

					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>