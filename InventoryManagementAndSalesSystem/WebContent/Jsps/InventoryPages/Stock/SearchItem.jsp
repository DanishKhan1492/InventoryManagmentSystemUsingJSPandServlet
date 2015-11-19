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
			<form action="/InventoryManagementAndSalesSystem/searchItem" method="post">
				<table>
					<tr>
						<td class="label"><label for="searchItem">Item Name</label></td>
						<td><input type="text" name="searchItem" class="input" id="searchItem" Placeholder="Enter Item Name for Search"></td>
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
						<th colspan="5">Item Information</th>
					</tr>
					<tr>
						<th>Item Id</th>
						<th>Item Name</th>
						<th>Category Name</th>
						<th>Item Quantity</th>
						<th>Item Price</th>
						<th>Update</th>
						<th>Delete</th>
					</tr>
				<c:url var="delete" value="/deleteItem"></c:url>
				<c:url var="update" value="/updateItemPage"></c:url>
				<c:forEach var="item" items="${items}">
					<tr>
						<td>${item.itemId}</td>
						<td>${item.itemName}</td>
						<td>${item.itemPrice}</td>
						<td>${item.itemQuantity}</td>
						<td>${item.itemCategory}</td>
						<td><a href="${update}?Item_ID=${item.itemId}" class="genericon genericon-trash">Update</a></td>
						<td><a href="${delete}?Item_ID=${item.itemId}" class="genericon genericon-trash">Delete</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>