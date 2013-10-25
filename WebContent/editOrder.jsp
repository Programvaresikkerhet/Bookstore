<div class="container">
    <h1>Edit Order</h1>
    <c:if test="${order.status<1}"> 
	    <form action="saveOrder.do" method="post">
	        <c:if test="${not empty messages}">
	            <c:forEach var="message" items="${messages}">
	                <div>
	                    <span class="error">${message}</span>
	                </div>
	            </c:forEach>
	        </c:if>        
	        
	        <h3>Select Address</h3>
	        <select name="shippingAddress">
		    	<c:forEach var="address" items="${addresses}" varStatus="counter">
		    		<c:choose>
	                	<c:when test="${order.address.id == address.id}">
	                		<option selected value="${address.id}">${address.address}</option>
	                	</c:when>
	                	<c:otherwise>
	                		<option value="${address.id}">${address.address}</option>
	                	</c:otherwise>
	                </c:choose>
			    </c:forEach>
	    	</select>
	        
	        <h3>Change quantities on order</h3>
	        <c:forEach var="orderItem" items="${order.items}">
	        		<input type="hidden" name="orderItemId" value="${orderItem.orderItmId}" />   
	        		    
                	<label> ${orderItem.bookTitle} </label>  
                	<input type="text" name="quantity" value="${orderItem.quantity}" />
                	
                	<br />
             </c:forEach>
            <br />
            
            <span><input type="submit" name="action" value="Cancel" /></span>
			<span><input type="submit" name="action" value="Save" /></span>
			<input type="hidden" name="orderId" value="${order.id}" /> 
    	</form>
    </c:if>
</div>