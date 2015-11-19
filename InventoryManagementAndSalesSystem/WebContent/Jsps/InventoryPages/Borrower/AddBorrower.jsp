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
		<div >
			<form action="/InventoryManagementAndSalesSystem/addBorrower" method="post" id="borrowerContainer">
				<table>
					<tr>
						<td class="label"><label for="borrowerName">Borrower Name</label></td>
						<td><input type="text" name="borrowerName" class="input" Placeholder="Enter Name" value="${validate.borrowerName}"></td>
						<td class="label"><label for="borrowerName" style="color: red;">${validate.errorBorrowerName}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="borrowerCnic">Borrower CNIC</label></td>
						<td><input type="text" name="borrowerCnic" class="input" Placeholder="Enter CNIC" value="${validate.borrowerCnic}"></td>
						<td class="label"><label for="borrowerCnic" style="color: red;">${validate.errorBorrowerCnic}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="borrowerAddress">Borrower Address</label></td>
						<td><input type="text" name="borrowerAddress" class="input" Placeholder="Enter Address" value="${validate.borrowerAddress}" ></td>
						<td class="label"><label for="borrowerAddress" style="color: red;">${validate.errorBorrowerAddress}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="borrowerPhone">Borrower Phone Number</label></td>
						<td><input type="text" name="borrowerPhone" class="input" Placeholder="Enter Phone Number" value="${validate.borrowerPhone}" ></td>
						<td class="label"><label for="borrowerPhone" style="color: red;">${validate.errorBorrowerPhone}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="borrowerDebt">Borrower Debt Amount</label></td>
						<td><input type="text" name="borrowerDebt" class="input" Placeholder="Enter Debt Amount" value="${validate.borrowerDebt}"></td>
						<td class="label"><label for="borrowerDebt" style="color: red;">${validate.errorBorrowerDebt}</label></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" Value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="message">
			<c:if test="${msg == 'Successfully Added'}">
				<span class="message" style="color:green; font-size: 20px;">${msg}</span>
			</c:if>
			<c:if test="${msg == 'Successfully Updated'}">
				<span class="message" style="color:green; font-size: 20px;">${msg}</span>
			</c:if>
			<c:if test="${msg == 'Deleted Successfully'}">
				<span class="message" style="color:red; font-size: 20px;">${msg}</span>
			</c:if>
		</div>
		<div id="borrowerTable">
			<table class="borrowerTable">
				<tbody>
				<tr>
						<th colspan="8">Borrower Information</th>
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
				<c:forEach var="borrower" items="${borrowers}">
					<tr>
						<td>${borrower.id}</td>
						<td>${borrower.borrowerName}</td>
						<td>${borrower.cnic}</td>
						<td>${borrower.address}</td>
						<td>${borrower.contactNumber}</td>
						<td>${borrower.debtAmount}</td>
						<td><a href="${update}?cnic=${borrower.cnic}" class="genericon genericon-trash">Update</a></td>
						<td><a href="${delete}?id=${borrower.id}" class="genericon genericon-trash">Delete</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
	</div>
</body>
</html>