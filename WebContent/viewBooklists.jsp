<div class="container">
    <h1>My Booklists</h1>
    <c:choose>
    	<c:when test="${empty customer}">
            <h2>Book not found!</h2>
            <div class = "index-item"><a href="debug/list_books.jsp">List books</a></div>
        </c:when>
        <c:otherwise>
	    
	    	<form action="viewBooklist" method="post">
	    	<ul style="list-style-type: none">
	    		<c:forEach items="${booklist}" var="item">
	    			<li><p>${item.title} -  ${item.description}</p> </li>
	    		</c:forEach>
	    	 </ul>
	    	</form>
	    	
	    	<br>
	    	<hr>
	    	<br>
	    	
	    	<form action="addBooklist.do" method="post">
	    		<input placeholder="Title" name="booklistTitle" type="text" style="width: 35%;"></input>
	    		<br>
	    		<textarea name="booklistDescription" rows="10" cols="50"></textarea>
	    		<br>
	    		<input type="submit" value="Add booklist"/>
	    	</form>
	   </c:otherwise>
	</c:choose>
</div>