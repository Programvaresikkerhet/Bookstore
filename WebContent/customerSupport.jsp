<div class="container">
    <h1>Customer Support</h1>
    <div class="general-form">
        <form method="post" action="customerSupport.do">
            <table class="general-table">
                <tr>
                    <td>Choose department:</td>
                    <td>
                        <select name="department">
                            <option selected >Sales</option>
                            <option >Technical support</option>
                        </select>
                    </td>
                </tr>
                
                <c:if test="${not empty messages}">
               		<c:forEach var="message" items="${messages}">
           				<div><span class="error">${message}</span></div>
           			</c:forEach>
       			</c:if>
                
                <tr>
                    <td>Subject</td>
                    <td><input name="subject" type="text"></input></td>
                </tr>
            </table>
            <div> <textarea name="content" rows="10" cols="40"></textarea></div>
            <div> <input type="submit" value="Send" /></div>
        </form>
    </div>
</div>