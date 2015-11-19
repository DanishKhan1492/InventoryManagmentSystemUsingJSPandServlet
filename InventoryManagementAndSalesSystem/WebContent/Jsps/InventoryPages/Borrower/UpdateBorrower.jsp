<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Borrower</title>
<link rel="stylesheet" href="resources/Css/Login.css" type="text\css">
</head>
<body>
	<div id="Container">
		<div id="menu"><jsp:include page="BorrowerMenu.jsp"></jsp:include></div>
		<div id="borrowerContainer">
			<form action="/InventoryManagementAndSalesSystem/updateBorrower" method="post">
				<table>
					<tr>
						<td><label for="borrowerName">Borrower Name</label></td>
						<td><input type="text" name="borrowerName" class="input" Placeholder="Enter Name" value="${borrower.borrowerName}"></td>
						<td><label for="borrowerName">${validate.borrowerName}</label></td>
					</tr>
					<tr>
						<td><label for="borrowerCnic">Borrower CNIC</label></td>
						<td><input type="text" name="borrowerCnic" class="input" Placeholder="Enter CNIC" value="${borrower.cnic}"></td>
						<td><label for="borrowerCnic">${validate.cnic}</label></td>
					</tr>
					<tr>
						<td><label for="borrowerAddress">Borrower Address</label></td>
						<td><input type="text" name="borrowerAddress" class="input" Placeholder="Enter Address" value="${borrower.address}" ></td>
						<td><label for="borrowerAddress">${validate.address}</label></td>
					</tr>
					<tr>
						<td><label for="borrowerPhone">Borrower Phone Number</label></td>
						<td><input type="text" name="borrowerPhone" class="input" Placeholder="Enter Phone Number" value="${borrower.contactNumber}" ></td>
						<td><label for="borrowerPhone">${validate.contactNumber}</label></td>
					</tr>
					<tr>
						<td><label for="borrowerDebt">Borrower Debt Amount</label></td>
						<td><input type="text" name="borrowerDebt" class="input" Placeholder="Enter Debt Amount" value="${borrower.debtAmount}"></td>
						<td><label for="borrowerDebt">${validate.debtAmount}</label></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" Value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>