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
                
            <c:if test="${not empty rateMessages}">
           	    <c:forEach var="message" items="${rateMessages}">
            		<div><span class="error">${message}</span></div>
            	</c:forEach>
       		 </c:if>

            
            <c:if test="${not empty customer}">
    	        <form action="addToBooklist.do" method="post">
		            <input type="hidden" name="isbn" value="${book.isbn13}" />       
		                <select name="selectedBooklist">
		 					<c:forEach items="${booklist}" var="list">
		    				    <option value="${list.id}">"${list.title}"</option>
						    </c:forEach>
						</select>
					<input type="submit" value="Add to booklist"/>              
		        </form>
            </c:if>     
            
            <c:if test="${not empty messages}">
           	    <c:forEach var="message" items="${messages}">
            		<div><span class="error">${message}</span></div>
            	</c:forEach>
       		</c:if>
               
   	        <div>
		        <form action="rateBook.do" method="post">
		            <input type="hidden" name="id" value="${book.id}" />
		            <input type="hidden" name="isbn" value="${book.isbn13}" />
			        <input name = "rate" type = "radio" value = "1"/>
			        <input name = "rate" type = "radio" value = "2"/>
			        <input name = "rate" type = "radio" value = "3"/>
			        <input name = "rate" type = "radio" value = "4"/>
			        <input name = "rate" type = "radio" value = "5"/>
			        <input type="submit" value="Rate!" />
			        <label>(Average ${book.averageRate})</label>
		        </form>
	        </div>          	
            <br>
            <hr>
            <br>
               
            <div>
            	<form action="addReview.do" method="post">
            		<input type="hidden" name="id" value="${book.id}" />
            		<input type="hidden" name="isbn" value="${book.isbn13}" />
            		<input type="text" name="review_text" maxlength="1000">
            		<br />
            		<input type="submit" value="Add review" />
            	</form>
            </div>
            
            <c:if test="${not empty book.reviews}">
	            <c:forEach var="reviewItem" items="${book.reviews}">
	                <form action="rateReview.do" method="post">
	                	<input type="hidden" name="id" value="${reviewItem.id}" />
	                	<input type="hidden" name="isbn" value="${book.isbn13}" />
	                	${reviewItem.reviewDate}  ${reviewItem.reviewText}
	                	
	                	
		                <input type="submit" name = "likes" class="thumbsUp" value = "" />
		                <label> (${reviewItem.likes}) </label>  
	                	
		                <input type="submit" name = "dislikes" class="thumbsDown" value = "" />
		                <label> (${reviewItem.dislikes}) </label>  
		            </form>
	            </c:forEach>
	        </c:if>
            <c:if test="${empty book.reviews}">
                <p>No reviews added yet!</p>
            </c:if>
	            
        </c:otherwise>
    </c:choose>
    <br>
</div>