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
   /*  select.find('option').remove(); */
      $.each(response, function(index, value) {
      $('<option>').val(value).text(value).appendTo(select);
  });
    });
}

 function populateBillId(obj){
	    var table;
	    if(obj.value=='Borrower'){
	    	table="borrowerbills";
	    }else if(obj.value=='Customer'){
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
	<div id="categoryDetails">
			<form action="/InventoryManagementAndSalesSystem/insertBillItems" method="post">
				<table>					
					<tr>
						<td class="label"><label for="category" class="label">Category Name</label></td>
						<td>
							<select id="category" name="itemCategory" id="itemCategory" onchange="populateItems(this)">
								<option selected="selected">${categ}</option> 
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
								<option selected="selected">${items[0].itemName}</option> 
							</select>
						</td>
						<td class="label"><label for="itemName" class="label" style="color: red;">${validate.errorItemName}</label></td>
					</tr>
					
					
					<tr>
						<td class="label"><label for="itemQuantity" class="label">Item Quantity</label></td>
						<td><input type="text" name="itemQuantity" class="input" id="itemQuantity" placeholder="Enter Item Price" value="${items[0].itemQuantity}"></td>
						<td class="label"><label for="itemQuantity" class="label" style="color: red;">${validate.errorItemQuantity}</label></td>
					</tr>
					<tr>
						<td><a href="/InventoryManagementAndSalesSystem/mainForm" class="button">Home Page</a></td>
						<td><input type="submit" value="Submit" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
</body>
</html>