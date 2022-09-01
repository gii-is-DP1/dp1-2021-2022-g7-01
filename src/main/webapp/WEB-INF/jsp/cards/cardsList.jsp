<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cards">
    <h2>Cards</h2>
		
    <table id="cardsTable" class="table table-striped">
        <thead>
        <tr>
       		<th style="width: 150px;">Name</th>
            <th style="width: 200px;">Image</th>
            <th style="width: 120px">Cards per Deck</th>
            <th style="width: 120px">Admin</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cards}" var="card">
            <tr>
                
                <td>
                    <c:out value="${card.name}"/>
                </td>
                <td>
                <spring:url value="/resources/images/cards/${card.name}.png" htmlEscape="true" var="cards"/>
										<img title="" src="${cards}" id="card" width="50%" height="50%"/>
                </td>
                <td>
                    <c:out value="${card.cardsPerDeck}"/>
                </td>
                <td>
                <c:if test="${authority==true}">
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Delete
						Card</a></c:if>
                </td>
            </tr>
        </c:forEach>
		</tbody>
    	</table>
</petclinic:layout>
