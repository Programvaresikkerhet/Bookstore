<div class="container">
    <h1>Book</h1>
    <c:choose>
        <c:when test="${empty book}">
            <h2>Book not found!</h2>
            <div class = "index-item"><a href="debug/list_books.jsp">List books</a></div>
        </c:when>
        <c:otherwise>
            <h2>${book.title.name}</h2>
            <div>
                <ul id="bookInformation" style="list-style-type: none">
                    <li>
                        <b>Authors:</b> 
                        <c:forEach items="${book.author}" var="author" varStatus="it">
                            ${author.name}<c:if test="${!it.last}">, </c:if>
                        </c:forEach>
                    </li>
                    <li><b>Publisher:</b> ${book.publisher.name}</li>
                    <li><b>Published:</b> ${book.published}</li>
                    <li><b>Edition:</b> ${book.edition} (${book.binding})</li>
                    <li><b>ISBN:</b> ${book.isbn13}</li>
                    <li><b>Price:</b> ${book.price}</li>
                </ul>
            </div>
            <div>
                ${book.description}
            </div>
            
                <form action="addBookToCart.do" method="post">
                    <input type="hidden" name="isbn" value="${book.isbn13}" />
                    <input type="text" name="quantity" value="1" />
                    <input type="submit" value="Add to cart" />
                </form>
                
                <c:if test="${not empty messages}">
           			<c:forEach var="message" items="${messages}">
            			<div><span class="error">${message}</span></div>
            		</c:forEach>
       			 </c:if>

                <c:choose>
                    <c:when test="${not empty customer}">
    	               <form action="addToBooklist.do" method="post">
		                    <input type="hidden" name="isbn" value="${book.isbn13}" />
		                
		                    <select name="selectedBooklist">
		                	    <option selected>Select booklist</option>
		 					    <c:forEach items="${booklist}" var="list">
		    					    <option value="${list.id}">"${list.title}"</option>
							    </c:forEach>
						    </select>
						    <input type="submit" value="Add to booklist"/>               
		                </form>
                    </c:when>
                
                    <c:otherwise>
	                    <p>Login to add book to booklist</p>
                    </c:otherwise>
               </c:choose> 
        </c:otherwise>
    </c:choose>
</div>

