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

	<div class="row">
		<div class="col-sm-10">
			<div align="center">
				<h2>EQUIPO GANADOR ${ winnerRol }</h2>
			</div>
		</div>
	</div>
	<table class="table table-striped" style="min-width: 720px">
		<thead>
			<tr>
				<th style="width: 20%;">Team</th>
				<th style="width: 40%;">Players</th>
				<th style="width: 20%">Points</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pointsPerRol.keySet()}" var="rol">
				<c:if test="${rol != 'SHOGUN' }">
					<tr>
						<td>${rol}</td>
						<td><c:forEach items="${game.listPlayers}" var="player">
								<c:if test="${player.rol==rol || (rol == 'SAMURAI' && player.rol == 'SHOGUN')}">
									<c:out value="${player.user.username} " />
								</c:if>
							</c:forEach></td>
						<td>${pointsPerRol.get(rol)}</td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>