<div class="container">
    <h1>Booklist</h1>
    <c:choose>
    <c:when test="${empty books}">
    	<p>Empty booklist</p>
    </c:when>
    <c:otherwise>
	    <ul style="list-style-type: none">
	    	
	    	<c:forEach items="${books}" var="item">
	    		<li> ${item.title.name}</li>
	    	</c:forEach>
	    </ul>
    </c:otherwise>
    </c:choose>
</div>