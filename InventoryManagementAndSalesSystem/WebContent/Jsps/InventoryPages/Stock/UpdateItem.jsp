<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update item</title>
<link rel="stylesheet" href="resources/Css/Login.css" type="text\css">
<link rel="stylesheet" href="resources/Css/updateItem.css" type="text\css">
</head>
<body>
	<div id="container">
		<div id="menu"><jsp:include page="StockMenu.jsp"></jsp:include></div>
		<div id="categoryDetails">
			<form action="/InventoryManagementAndSalesSystem/updateItem" method="post">
				<input type="hidden" name="oldPrice" value="${items.itemPrice}">
				<table>					
					<tr>
						<td class="label"><label for="itemName" class="label">Item Name</label></td>
						<td><input type="text" name="itemName" class="input" id="itemName" placeholder="Enter item Name" value="${items.itemName}"></td>
						<td class="label"><label for="itemName" class="label" style="color: red;">${validate.errorItemName}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="itemQuantiy" class="label">Item Quantity</label></td>
						<td><input type="text" name="itemQuantity" class="input" id="itemQuantity" placeholder="Enter Item Name" value="${items.itemQuantity}"></td>
						<td class="label"><label for="itemQuantity" class="label" style="color: red;">${validate.errorItemQuantity}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="itemPrice" class="label">Item Price</label></td>
						<td><input type="text" name="itemPrice" class="input" id="itemPrice" placeholder="Enter Item Price" value="${items.itemPrice}"></td>
						<td class="label"><label for="itemPrice" class="label" style="color: red;">${validate.errorItemPrice}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="category" class="label">Category Name</label></td>
						<td>
							<select id="category" name="itemCategory">
								<c:choose>
									<c:when test="${items.itemCategory != null}">
										<option selected="selected"> ${items.itemCategory} </option>
									</c:when>
									<c:otherwise>
										<option selected="selected">Select Category</option> 
									</c:otherwise>
								</c:choose>
								<c:forEach var="category" items="${categories}">
									<option>${category.categoryName}</option>
								</c:forEach>
							</select>
						</td>
						<td class="label"><label for="itemCategory" class="label" style="color: red;">${validate.errorItemCategory}</label></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>