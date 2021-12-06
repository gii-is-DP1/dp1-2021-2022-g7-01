<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cards">
    <h2>Cards</h2>
    <div style="width: 100%; display: flex; justify-content: flex-end">
			<a href="/cards/new" class="btn btn-default">Create card</a>
		</div>
		
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
                <spring:url value="/resources/images/cards/${card.image}" htmlEscape="true" var="cards"/>
										<img title="" src="${cards}" id="card" width="50%" height="50%"/>
                </td>
                <td>
                    <c:out value="${card.cardsPerDeck}"/>
                </td>
                <td>
                <spring:url value="cards/edit/{id_card}" var="editUrl">
						<spring:param name="id_card" value="${card.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit
						Card</a>
                    <spring:url value="cards/delete/{id_card}" var="editUrl">
						<spring:param name="id_card" value="${card.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Delete
						Card</a>
                </td>
            </tr>
        </c:forEach>
		</tbody>
    	</table>
</petclinic:layout>
