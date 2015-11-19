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
			<form action="/InventoryManagementAndSalesSystem/searchBorrower" method="post">
				<table>
					<tr>
						<td class="label"><label for="borrowerCnic">Borrower CNIC</label></td>
						<td><input type="text" name="borrowerCnic" class="input" Placeholder="Enter CNIC" ></td>
						<td class="label"><label for="borrowerCnic" style="color: red;">${validate.borrowerCnic}</label></td>
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
		
		<div id="borrowerTable">
			<table class="borrowerTable">
				<tbody>
				<tr>
						<th colspan="5">Borrower Information</th>
					</tr>
					<tr>
						<th>Borrower Id</th>
						<th>Borrower Name</th>
						<th>Borrower CNIC</th>
						<th>Borrower Address</th>
						<th>Borrower Phone Number</th>
						<th>Borrower Debt Amount</th>
						<th>Update</th>
						<th>Delete</th>
					</tr>
				<c:url var="delete" value="/deleteBorrower"></c:url>
				<c:url var="update" value="/updateBorrowerPage"></c:url>
				<%-- <c:forEach var="borrower" items="${borrowers}"> --%>
					<tr>
						<td>${searchBorrowers.id}</td>
						<td>${searchBorrowers.borrowerName}</td>
						<td>${searchBorrowers.cnic}</td>
						<td>${searchBorrowers.address}</td>
						<td>${searchBorrowers.contactNumber}</td>
						<td>${searchBorrowers.debtAmount}</td>
						<td><a href="${update}?cnic=${searchBorrowers.cnic}" class="genericon genericon-trash">Update</a></td>
						<td><a href="${delete}?id=${searchBorrowers.id}" class="genericon genericon-trash">Delete</a></td>
					</tr>
				<%-- </c:forEach> --%>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>