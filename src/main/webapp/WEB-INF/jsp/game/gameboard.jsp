<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="gameboard">
	<h2>
		<div align="center">
			<c:choose>
				<c:when test="${gameDeck !=null}">
					<div>
						hay gamedeck
					</div>
					
					<c:forEach var="i" begin="0" end="${gameDeck.getCardList().size()-1}">
						<p>${gameDeck.getCardList().get(i).getName()}</p>
					</c:forEach>
				</c:when>
				
			</c:choose>
		</div>
	</h2>


</petclinic:layout>