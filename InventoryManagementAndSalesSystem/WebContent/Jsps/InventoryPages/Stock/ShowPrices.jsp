<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Item</title>
<link rel="stylesheet" href="resources/Css/searchItem.css" type="text\css">
<link rel="stylesheet" href="resources/Css/Login.css" type="text\css">
</head>
<body>
	<div id="container">
		<div id="menu">
			<jsp:include page="StockMenu.jsp"></jsp:include>
		</div>
		<div id="searchArea">
			<form action="/InventoryManagementAndSalesSystem/searchItemPrice" method="post">
				<table>
					<tr>
						<td class="label"><label for="searchItem">Item Name</label></td>
						<td><input type="text" name="searchItemPrice" class="input" id="searchItemPrice" Placeholder="Enter Item Name for Price Search"></td>
						<td><label for="searchItem" style="color:red">${validate.msg}</label></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" Value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="itemTable">
			<table class="itemTable">
				<tbody>
				<tr>
						<th colspan="5">Price Information</th>
					</tr>
					<tr>
						<th>Price Change Id</th>
						<th>Item Name</th>
						<th>Old Price</th>
						<th>New Price</th>
						<th>Date</th>
					</tr>

				<c:forEach var="price" items="${prices}">
					<tr>
						<td>${price.priceChangeId}</td>
						<td>${price.itemName}</td>
						<td>${price.oldPrice}</td>
						<td>${price.newPrice}</td>
						<td>${price.date}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>