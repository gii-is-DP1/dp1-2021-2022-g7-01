<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Profile">
	<h2>Game History</h2>

	<table id="gameHistory" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Status</th>
				<th style="width: 200px;">Rol</th>
				<th style="width: 200px;">Honor points</th>
				<th style="width: 120px">Start date</th>
				<th style="width: 120px">End date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listPlayers}" var="player">
				<tr>
					<td><c:out value="${player.wonGame ? 'Victory' : 'Defeat'}"/>
					</td>
					<td><c:out value="${player.rol}" /></td>
					<td><c:out value="${player.honor}" /></td>
					<td><c:out value="${player.game.startDate}" /></td>
					<td><c:out value="${player.game.endDate}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
