<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Items</title>
<link rel="stylesheet" href="resources/Css/insertItem.css" type="text\css">
<link rel="stylesheet" href="resources/Css/Login.css" type="text\css">
</head>
<body>
	<div id="container">
		<div id="menu"><jsp:include page="StockMenu.jsp"></jsp:include></div>
		<div id="itemDetails">
			<form action="/InventoryManagementAndSalesSystem/insertItem" method="post">
				<table>					
					<tr>
						<td class="label"><label for="itemName" class="label">Item Name</label></td>
						<td><input type="text" name="itemName" class="input" id="itemName" placeholder="Enter item Name" value="${validate.itemName}"></td>
						<td class="label"><label for="itemName" class="label" style="color: red;">${validate.errorItemName}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="itemQuantiy" class="label">Item Quantity</label></td>
						<td><input type="text" name="itemQuantity" class="input" id="itemQuantity" placeholder="Enter Item Name" value="${validate.itemQuantity}"></td>
						<td class="label"><label for="itemQuantity" class="label" style="color: red;">${validate.errorItemQuantity}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="itemPrice" class="label">Item Price</label></td>
						<td><input type="text" name="itemPrice" class="input" id="itemPrice" placeholder="Enter Item Price" value="${validate.itemPrice}"></td>
						<td class="label"><label for="itemPrice" class="label" style="color: red;">${validate.errorItemPrice}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="category" class="label">Category Name</label></td>
						<td>
							<select id="category" name="itemCategory">
								<c:choose>
									<c:when test="${validate.itemCategory != null}">
										<option selected="selected"> ${validate.itemCategory} </option>
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
		<div id="itemTable">
			<table class="itemTable">
				<tbody>
				<tr>
						<th colspan="8">Item Information</th>
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