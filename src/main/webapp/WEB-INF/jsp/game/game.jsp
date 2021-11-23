<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="game">
 <style>
        img.estrella {
  	position: relative;
 	 top: 50px;
 	 left: -65px;
	}
	</style>

    <h2>
    <div align="center">
    
        
				<h2>Bienvenido a la sala #${gameId} creada por ${listPlayer.get(0).getUser().getUsername()}</h2>
				<h2>${now}</h2>
				<p>Jugadores en sala (<b>${listPlayer.size()}</b>)</p>
				<br><br>
				<div class="container">
				
				<div align="center" class="col-sm-10">
				<c:forEach items="${listPlayer}" var="player">
				<c:choose>
				<c:when test="${user==player.user.username}">
				<img style="content: ${player.user.username}" title="${player.user.username}" align="left" class="img-responsive" src="/resources/images/user.png" height="80" width="90" vspace="5" hspace="20"></img>
				</c:when>
				<c:otherwise>				
				<img  title="${player.user.username}" align="left" class="img-responsive" src="/resources/images/user.png" height="50" width="70" vspace="10" hspace="10"></img>				
				</c:otherwise>
				</c:choose>
				<c:choose>
				<c:when test="${listPlayer.get(0).getUser().getUsername()==player.user.username}">
				
				<div >
				
				<img class="estrella" title="${player.user.username}" align="left" class="img-responsive" src="/resources/images/star.png" height="20" width="20" vspace="10" hspace="10"></img>
	</div>
				
				</c:when>
				<c:otherwise>
				
				</c:otherwise></c:choose>
				</c:forEach>
				
				<c:if test="${listPlayer.size()<7}">
				<img  title="${player.user.username}" align="left" class="img-responsive" src="/resources/images/anadir.png" height="50" width="70" hspace="20" vspace="10"></img>						
				</c:if>
				
				
				
				</div>
				
				
				<div align="right" class="col-sm-16">
				<h2>Amigos (${listFriends.size()})</h2>
				<c:if test="${listFriends.size()!=0}">
				<c:forEach var="i" begin="0" end="${listFriends.size()-1}">
				
				<p>${listFriends.get(i).getUsername()}</p></c:forEach>
				</c:if>
				<br>
				
				</div>
				</div>

    			</h2>
    			<div align="center">
    			<br>
    			
    			<c:if test="${listPlayer.size()>=4}">
				<button style="background-color: #00FF00">Empezar partida</button>
				</c:if>
				<c:if test="${listPlayer.size()<4}">
				<b>TIENEN QUE HABER AL MENOS 4 JUGADORES PARA EMPEZAR LA PARTIDA</b><br>
				<button disabled="disabled" style="background-color: #FF0000">Empezar partida</button>
				
				
				</c:if>
				<c:if test="${listPlayer.get(0).getUser().getUsername()==user}">
    			<spring:url value="delete/{gameId}" var="editUrl">
						<spring:param name="gameId" value="${gameId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Delete
						Game</a></c:if>
				</div>
    </petclinic:layout>