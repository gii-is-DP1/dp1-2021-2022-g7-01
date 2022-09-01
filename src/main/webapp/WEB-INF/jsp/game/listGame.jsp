<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="listGame">
	<h2>Games in progress</h2>

	<table id="gamesInProgressTable" class="table table-striped" style="min-width: 720px">
		<thead>
			<tr>
				<th style="width: 100px;">Id</th>
				<th style="width: 200px;">Start date</th>
				<th style="width: 200px;">Creator</th>
				<th style="width: 200px;">List of players</th>
				<th style="width: 200px;">View</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${gamesInProgress}" var="game">
				<tr>
					<td>${game.id}</td>
					<td>${game.startDate}</td>
					<td><spring:url value="/users/profile/{username}"
							var="userUrl">
							<spring:param name="username" value="${game.creator.username}" />
						</spring:url> <a href="${fn:escapeXml(userUrl)}"><c:out
								value="${game.creator.username}" /></a></td>
					<td><c:forEach items="${game.listPlayers}" var="player">
							<spring:url value="/users/profile/{username}" var="userUrl">
								<spring:param name="username" value="${player.user.username}" />
							</spring:url>
							<a href="${fn:escapeXml(userUrl)}"><c:out
									value="${player.user.username} " /></a>
						</c:forEach></td>
						
					<td><div style="width: 100%; display: flex; justify-content: flex-end">

				<a href="/game/continue/${game.id}" class="btn btn-default">View</a>

			</div>
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${gamesInProgress.isEmpty()}">
		<p style="margin-top: -10px; margin-bottom: 20px">There's no game
			in progress</p>
	</c:if>
	<h2>Games ended</h2>
	<table id="gamesEndedTable" class="table table-striped"
		style="min-width: 720px">
		<thead>
			<tr>
				<th style="width: 4%">Id</th>
				<th style="width: 16%">Start date</th>
				<th style="width: 16%">End date</th>
				<th style="width: 13%">Creator</th>
				<th style="mwidth: 48%">List of players</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${gamesEnded}" var="game">
				<tr>
					<td>${game.id}</td>
					<td>${game.startDate}</td>
					<td>${game.endDate}</td>
					<td><spring:url value="/users/profile/{username}"
							var="userUrl">
							<spring:param name="username" value="${game.creator.username}" />
						</spring:url> <a href="${fn:escapeXml(userUrl)}"><c:out
								value="${game.creator.username}" /></a></td>
					<td><c:forEach items="${game.listPlayers}" var="player">
							<spring:url value="/users/profile/{username}" var="userUrl">
								<spring:param name="username" value="${player.user.username}" />
							</spring:url>
							<a href="${fn:escapeXml(userUrl)}"><c:out
									value="${player.user.username} " /></a>
						</c:forEach></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${gamesEnded.isEmpty()}">
		<p style="margin-top: -10px;">There's no game ended</p>
	</c:if>
</petclinic:layout>