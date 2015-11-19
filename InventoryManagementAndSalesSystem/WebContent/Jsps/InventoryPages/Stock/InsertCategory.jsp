<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Category</title>
<link rel="stylesheet" href="resources/Css/InsertCategoryCss.css" type="text\css">
<link rel="stylesheet" href="resources/Css/Login.css" type="text\css">

</head>
<body>
	<div id="container">
		<div id="menu"><jsp:include page="StockMenu.jsp"></jsp:include></div>
		<div id="categoryDetails">
			<form action="/InventoryManagementAndSalesSystem/insertCategory" method="post">
				<table>
					<tr>
						<td class="label"><label for="catName" class="label">Category Name</label></td>
						<td><input type="text" name="catName" class="input" id="catName" placeholder="Enter Category Name" value="${validate.catName}"></td>
						<td class="label"><label for="catName" class="label" style="color: red;">${validate.errorCategoryName}</label></td>
					</tr>
					<tr>
						<td class="label"><label for="catUnit" class="label">Category Unit</label></td>
						<td><select class="select" name="catUnit">
								<option>Select Unit</option>
								<option>KG</option>
								<option>Packet</option>
						</select></td>
						<td class="label"><label for="errorCatUnit" class="label" style="color: red;">${validate.errorCategoryName}</label></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>

		<div id="categoryTable">
			<table class="catTable">
				<tbody>
				<tr>
						<th colspan="5">Category Information</th>
					</tr>
					<tr>
						<th>Category Id</th>
						<th>Category Name</th>
						<th>CategoryUnit</th>
						<th>Delete</th>
					</tr>
				<c:url var="delete" value="/deleteCategory"></c:url>
				<c:forEach var="category" items="${categories}">
					<tr>
						<td>${category.id}</td>
						<td>${category.categoryName}</td>
						<td>${category.categoryUnit}</td>
						<td><a href="${delete}?Cat_Name=${category.categoryName}" class="genericon genericon-trash">Delete</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>

	</div>
</body>
</html>