<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<petclinic:layout pageName="game">

<style>
img.estrella {
	position: absolute;
	bottom: 100px;
}
</style>
	<c:set value="${listPlayer.get(0).getUser().getUsername()==user}"
		var="isLoggedUserGameCreator" />
	<c:set value="${listPlayer.size()<7}" var="isGameFull" />
	<div class="row">
		<div class="col-sm-10">
			<div align="center">
				<h2>Welcome to the game #${gameId} created by
					${listPlayer.get(0).getUser().getUsername()}</h2>
				<h2>${now}</h2>
				<p>
					Players in game (<b>${listPlayer.size()}</b>)
				</p>
				<br> <br>
				<div class="container">
					<div id="playerRow" style="display: flex; width: 100%">
						<c:forEach items="${listPlayer}" var="player">
							<c:set value="${user==player.user.username}" var="isLoggedUser" />
							<c:set
								value="${listPlayer.get(0).getUser().getUsername()==player.user.username}"
								var="isCreatorPlayer" />
							<div id="playerCard"
								style="display: flex; flex-direction: column; justify-content: center; align-items: center; margin: 0 10px">
								<c:choose>
									<c:when test="${isLoggedUser}">
										<img style="content: ${player.user.username}"
											title="${player.user.username}" align="left"
											class="img-responsive" src="/resources/images/user.png"
											height="80" width="90" vspace="5" hspace="20"></img>
									</c:when>
									<c:otherwise>
										<img title="${player.user.username}" align="left"
											class="img-responsive" src="/resources/images/user.png"
											height="50" width="70" vspace="10" hspace="10"></img>
										<c:if test="${isLoggedUserGameCreator}">
											<spring:url value="/game/{gameId}/players/delete/{playerId}"
												var="editUrl">
												<spring:param name="playerId" value="${player.id}" />
												<spring:param name="gameId" value="${gameId}" />
											</spring:url>
											<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Kick
												${player.user.username}</a>
										</c:if>
									</c:otherwise>
								</c:choose>
								<c:if test="${isCreatorPlayer}">
									<div style="postiion: relative">
										<img class="estrella" title="${player.user.username}"
											align="left" class="img-responsive"
											src="/resources/images/star.png" height="20" width="20"
											vspace="10" hspace="10"></img>
									</div>
								</c:if>
							</div>
						</c:forEach>
						<c:if test="${isGameFull}">
							<div>
								<img title="${player.user.username}" align="left"
									class="img-responsive" src="/resources/images/anadir.png"
									height="50" width="70" hspace="20" vspace="10"></img>
							</div>
						</c:if>
					</div>
					<div align="center">
						<br>

						<c:if test="${listPlayer.size()>=4}">
							<spring:url value="start/{gameId}" var="startUrl">
								<spring:param name="gameId" value="${gameId}" />
							</spring:url>
							
							<c:if test="${listPlayer.get(0).getUser().getUsername()==user}">
								<a href="${fn:escapeXml(startUrl)}" class="btn btn-success">Start
								Game</a></c:if>
						</c:if>
						<c:if test="${listPlayer.size()<4 }">
							<b>THERE MUST BE AT LEAST 4 PLAYERS TO START THE GAME</b>
							<br>
							
							<c:if test="${listPlayer.get(0).getUser().getUsername()==user}">
							<button disabled="disabled" class="btn btn-secondary">Start Game</button></c:if></c:if>
						
						<c:if test="${listPlayer.get(0).getUser().getUsername()==user}">
							<spring:url value="delete/{gameId}" var="editUrl">
								<spring:param name="gameId" value="${gameId}" />
							</spring:url>
							<a href="${fn:escapeXml(editUrl)}" class="btn btn-danger">Delete
								Game</a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div align="right" class="col-sm-2 ">
			<h2>Friends (${listFriends.size()})</h2>
			<c:if test="${listFriends.size()!=0}">
				<c:forEach var="i" begin="0" end="${listFriends.size()-1}">
					<p>${listFriends.get(i).getUsername()}</p>					
							<form:form class="form-horizontal"
							action="/game/${gameId}/invitation/${listFriends.get(i).getUsername()}"
							id="edit-invitation-form">
							<c:forEach items="${listPlayer}" var="player">
							<c:if test="${player.user.username.contains(user)}">
							<button class="btn btn-default" type="submit">Invite</button></c:if></c:forEach>
						</form:form>					
				</c:forEach>
			</c:if>
			<br>

		</div>
	</div>
</petclinic:layout>