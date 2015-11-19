<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Bill</title>
<link rel="stylesheet" href="resources/Css/Login.css" type="text\css">
<link rel="stylesheet" href="resources/Css/CreateBill.css" type="text\css">
<script src="resources/js/moment.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">	
 function populateItems(obj){
    var itemCategory = obj.value;
    $.get('populateBillItems', {
    	catName : itemCategory
    }, function(response) {

    var select = $('#itemName');
    select.find('option').remove();
      $.each(response, function(index, value) {
      $('<option>').val(value).text(value).appendTo(select);
  });
    });
}

 function populateBillId(obj){
	    var table;
	    if(obj.value=='Borrower'){
	    	table="borrowerbills";
	    	$("#customerName").prop("readonly", true);
	    }else if(obj.value=='Customer'){
	    	$("#customerName").prop("readonly",false);
	    	table="customerbills";
	    }
	    $.get('populateBillId', {
	    	tableName : table
	    }, function(response) {
	    	var id=response;
	    	$("#billId").val(id);
	    });
}
 
 $(document).ready(function getData() {

	    $("#date").val( moment().format('D/MM/YYYY') );
	    
	    $("#discount").val(0);
	    
});
</script>

</head>
<body>
	<div id="container">
		<div id="categoryDetails">
			<form action="/InventoryManagementAndSalesSystem/insertBillItems" method="post">
				<table>					
					<tr>
						<td class="label"><label for="category" class="label">Category Name</label></td>
						<td>
							<select id="category" name="itemCategory" id="itemCategory" onchange="populateItems(this)">
								<option selected="selected">Select Category</option> 
								<c:forEach var="category" items="${categories}">
									<option>${category.categoryName}</option>
								</c:forEach>
							</select>
						</td>
						<td class="label"><label for="itemCategory" class="label" style="color: red;">${validate.errorCategoryName}</label></td>
					</tr>
					
					<tr>
						<td class="label"><label for="itemName" class="label">Item Name</label></td>
						<td>
							<select name="itemName" id="itemName">
								<option selected="selected">Select Items</option> 
							</select>
						</td>
						<td class="label"><label for="itemName" class="label" style="color: red;">${validate.errorItemName}</label></td>
					</tr>
					
					
					<tr>
						<td class="label"><label for="itemQuantity" class="label">Item Quantity</label></td>
						<td><input type="text" name="itemQuantity" class="input" id="itemQuantity" placeholder="Enter Item Price" value="${items.itemQuantity}"></td>
						<td class="label"><label for="itemQuantity" class="label" style="color: red;">${validate.errorItemQuantity}</label></td>
					</tr>
					<tr>
						<td><a href="/InventoryManagementAndSalesSystem/mainForm" class="button">Home Page</a></td>
						<td><input type="submit" value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="itemTable">
			<table class="itemTable">
				<tbody>
				<tr>
						<th colspan="6">Bill Item Information</th>
					</tr>
					<tr>
						<th>Item Name</th>
						<th>Item Quantity</th>
						<th>Item Price</th>
						<th>Item Total Price</th>
						<th>Update</th>
						<th>Delete</th>
					</tr>
				<c:url var="delete" value="/deleteBillItems"></c:url>
				<c:url var="update" value="/updateBillItemsPage"></c:url>
				<c:forEach var="item" items="${billItems}">
					<tr>
						<td>${item.itemName}</td>
						<td>${item.itemQuantity}</td>
						<td>${item.itemPrice}</td>
						<td>${item.totalItemPrice}</td>
						<td><a href="${update}?itemName=${item.itemName}" class="genericon genericon-trash">Update</a></td>
						<td><a href="${delete}?itemName=${item.itemName}" class="genericon genericon-trash">Delete</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div id="submitform">
			<form action="createBill" method="post">
				<div id="billSection">
					<select id="category" name="billCategory" id="billCategory" onchange="populateBillId(this)">
						<option selected="selected">Customer</option>
						<option>Borrower</option> 
					</select>
					<input type="text" class="input" placeholder="Bill ID" id="billId" name="billId" value="${BillId}" readonly/>
			
					<input type="text" class="input" placeholder="Customer Name" name="customerName" id="customerName" style="${validate.errorCustomerName}"/>
				
					<input type="text" class="input"  placeholder="CNIC NO XXXXX-XXXXXXX-X" name="customerCnic" style="${validate.msg1}" />

					<input type="text" class="input" value="${totalAmount}" name="totalAmount" placeholder="Total Amount" readonly>
		
					<input type="text" class="input" name="discount" placeholder="Discount" style="${validate.msg2}">
				
					<input type="text" class="input" name="amountPaid" placeholder="Amount Paid" style="${validate.msg3}">
			
					<input type="text" class="input" name="date" value="getData()" id="date"  readOnly />
				
					<input type="submit" Value="Submit" id="submit" class="button"/>
					
				</div>
			</form>
	</div>
		
	</div>
</body>
</html>