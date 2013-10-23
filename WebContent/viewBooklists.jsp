<div class="container">
    <h1>My Booklists</h1>
    <c:choose>
    	<c:when test="${empty booklist}">
            <h2>There is booklists!</h2>
        </c:when>
        <c:otherwise> 
	    	<form action="viewBooklist.do" method="get">
	    	<ul style="list-style-type: none">
	    		<c:forEach items="${booklist}" var="item">
	    			<li><a href='viewBooklist.do?id=${item.id}'>${item.title}</a></li>
	    		</c:forEach>
	    	 </ul>
	    	</form>
	    	
	    	<br>
	    	<hr>
	    	<br>
	    </c:otherwise>
	    </c:choose>
	    <c:choose>	
	    <c:when test="${not empty customer}">	
	    	<form action="addBooklist.do" method="post">
	    		<input placeholder="Title" name="booklistTitle" type="text" style="width: 35%;"></input>
	    		<br>
	    		<textarea name="booklistDescription" rows="10" cols="50"></textarea>
	    		<br>
	    		<input type="submit" value="Add booklist"/>
	    	</form>
	    </c:when>
	    <c:otherwise>
	    	<p>Login to add new booklists</p>
	    </c:otherwise>	
	    </c:choose>
	
</div>