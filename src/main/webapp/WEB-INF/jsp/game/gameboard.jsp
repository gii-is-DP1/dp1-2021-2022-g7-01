<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="samuraisword.game.GamePhase"%>

<style>
body {
	background-image: url("/resources/images/wood2.jpg");
	background-position: center;
	background-size: cover;
	background-opacity: 50%;
	justify-content: center;
	background-height: 100%;
	padding: 0;
	margin: 0;
}

.innerCircle {
    left: calc(15% - 25px);
    top: calc(1% - 25px);
    width: 50px;
    margin: 0 auto;
}

/*Angulo por defecto (separacion angular con 4 jugadores)*/
:root {
	--angle : 90deg;
}
/*el primero no lo desplazamos*/
.innerCircle:nth-child(1n) {
    transform: translateX(80px);
    left:100px;
    top:80px;
    position: absolute;
}

/*desplazamos el resto de jugadores; cada cual mÃ¯Â¿Â½s lejos con respecto al primero*/
.innerCircle:nth-child(2n) {
    transform: rotate(calc(var(--angle))) translateX(80px);
    left:100px;
    top:80px;
    position: absolute;
}

.innerCircle:nth-child(3n) {
    transform: rotate(calc(var(--angle)*2)) translateX(80px);
    left:100px;
    top:80px;
    position: absolute;
}

.innerCircle:nth-child(4n) {
    transform: rotate(calc(var(--angle)*3)) translateX(80px);
    left:100px;
    top:80px;
    position: absolute;
}

.innerCircle:nth-child(5n) {
    transform: rotate(calc(var(--angle)*4)) translateX(80px);
    left:100px;
    top:80px;
    position: absolute;
}

.innerCircle:nth-child(6n) {
    transform: rotate(calc(var(--angle)*5)) translateX(80px);
    left:100px;
    top:80px;
    position: absolute;
}

.innerCircle:nth-child(7n) {
    transform: rotate(calc(var(--angle)*6)) translateX(80px);
    left:100px;
    top:80px;
    position: absolute;
}

.row {
	display: flex;
	flex-wrap: wrap;
}

.container {
	margin: 5px;
}

.circle {
	width: 400px;
	height: 200px;
	background-color: red;
}

.selected-player {
	width: 600px;
	height: 200px;
	background-color: green;
}

.buttons {
	width: 300px;
	height: 200px;
	background-color: blue;
}

.deck {
	width: 400px;
    height: 200px;
    background-color: pink;
    display:flex;
    flex-direction:row;
}

.your-player {
	width: 400px;
	height: 200px;
	background-color: white;
}

.blank-space {
	width: 500px;
	height: 200px;
	background-color: purple;
}

.discard {
	width: 400px;
	height: 200px;
	background-color: black;
}

.equipment {
    width: 500px;
    height: 200px;
    background-color: silver;
    outline-style: solid;
    outline-color: black;
    outline-width: 10px;
    border-radius: 20px;
}
*/

.hand {
	width: 100%;
	height: 200px;
	background-color: grey;
}

table {
    width: 100%;
    border-collapse: collapse;
}

.deck .box {
    text-align:justify;
}

#btn-end-turn{
    background-color: gray;
    margin-bottom: 3%;
}

.button{
	width:100px;
	height: 25%;
	border-radius: 15px;
	justify-content: center;
	cursor: pointer;
  	outline: none;
	border: none;
    color: black;
    box-shadow: 0 9px #999;
}

#div1, #div2 {margin: 20px}
#card {margin: 8px; height:200px; width:27%; float:left}
#select {float: right; margin: 5px}

.viewAttackCards{
	visibility: hidden;
  	opacity: 0;
  	transition: opacity .2s, visibility .2s;
}

.division{
	display: none;
}

</style>


<c:set value="${game.listPlayers}" var="listPlayer" />
<c:set value="${game.deck}" var="deck" />
<c:set value="${game.discardPile}" var="discardPile" />
<c:set value="${game.currentPlayer.user}" var="currentUser" />

<!-- EN CASO DE QUE NO SEAN 4 JUGADORES REAJUSTAMOS EL ANGULO DE SEPARACION QUE SERA DADO POR 360/NÃ¯Â¿Â½jugadores -->

<c:if test="${listPlayer.size()==5}">
	<script type="text/javascript">
		var rootstyle = document.querySelector(":root").style;
		rootstyle.setProperty('--angle', '72deg');
	</script>
</c:if>
<c:if test="${listPlayer.size()==6}">
	<script type="text/javascript">
		var rootstyle = document.querySelector(":root").style;
		rootstyle.setProperty('--angle', '60deg');
	</script>
</c:if>
<c:if test="${listPlayer.size()==7}">
	<script type="text/javascript">
		var rootstyle = document.querySelector(":root").style;
		rootstyle.setProperty('--angle', '51deg');
	</script>
</c:if>

<div class="row">

	<!-- PLAYERS CIRCLE -->
	<div class="circle container">
	<c:forEach items="${listPlayer}" var ="player" varStatus="loop">
			<div class="innerCircle"> 
				<!-- div intermediario para corregir la rotacion dentro -->
			    <div style="transform: rotate(calc(360deg - var(--angle)* ${ loop.index })">
			    	<div style="border-radius: 10px;">
				    <c:choose>
				    	<c:when test="${ game.currentPlayer.equals(player) }">
				    		<button class = "player" style="border-radius: 10px; background-color: green;" onclick="myFunction(${player.getUser().getUsername()})"> ${player.getUser().getUsername()} </button>
				    	</c:when>
				    	<c:when test="${ player.getRol().toString().equals('SHOGUN') }">
				    		<button class = "player" style="border-radius: 10px; background-color: blue;" onclick="myFunction(${player.getUser().getUsername()})"> ${ player.getUser().getUsername() } </button>
				    	</c:when>
				    	<c:when test="${ player.getUser().getUsername().equals(POVplayer.getUser().getUsername()) }">
				    		<button class = "player" style="border-radius: 10px; background-color: yellow;" onclick="myFunction(${player.getUser().getUsername()})"> ${ player.getUser().getUsername() } </button>
				    	</c:when>
				    	
				    	<c:otherwise>
				    		<button class = "player" style="border-radius: 10px;" onclick="myFunction(${player.getUser().getUsername()})"> ${ player.getUser().getUsername() } </button>
				    	</c:otherwise>
				    </c:choose>
				    
			    	
						<!-- <div style="display: inline-block;" class="img-wrap">
						
		    				<img src="/resources/images/${ player.getCharacter().getImage() }" alt="character" style="height: 50%; max-height:100%; max-width:100%;" />
		    			</div> -->
		    		</div>
				</div>
    		</div>
    	</c:forEach>
	</div>

	<!-- SELECTED PLAYER INFO -->
	<div class="selected-player container">
	<c:forEach items="${listPlayer}" var ="player" varStatus="loop">
	<div id="${player.getUser().getUsername()}" class="division" style="display: none">
		<table>
			<tr>
				<th>
					<h2 style ="color:#f2f2f2" >Vida: ${ player.getCurrentHearts() }</h2>
				</th>
				<th>
					<h2 style ="color:#f2f2f2" >Honor: ${ player.getHonor() }</h2>
				</th>
				<th>
					<h2 style ="color:#f2f2f2" >Cartas: ${ player.getHand().size() }</h2>
				</th>
			</tr>
			<tr>
				<td >
					<spring:url value="/resources/images/cards/armadura.png" htmlEscape="true" var="equipment1" /> 
					<c:choose>
						<c:when test="${ player.getDistanceBonus()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment1}" id="equipment1" /> x${ player.getDistanceBonus() }</p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment1}" id="equipment1" /> x${ player.getDistanceBonus() }</p>
						</c:otherwise>
					</c:choose>
					
					
				</td>
				<td>
					<spring:url value="/resources/images/cards/desenvainado rapido.png" htmlEscape="true" var="equipment2" />
					<c:choose>
						<c:when test="${ player.getDamageBonus()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment2}" id="equipment2" /> x${ player.getDamageBonus() } </p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment2}" id="equipment2" /> x${ player.getDamageBonus() } </p>
						</c:otherwise>
					</c:choose> 
					
				</td>
				<td>
					<spring:url value="/resources/images/cards/concentracion.png" htmlEscape="true" var="equipment3" /> 
					<c:choose>
						<c:when test="${ player.getWeaponBonus()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment3}" id="equipment3" /> x${ player.getWeaponBonus() } </p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment3}" id="equipment3" /> x${ player.getWeaponBonus() } </p>

						</c:otherwise>
					</c:choose>
					
				</td>
				<td>
					<spring:url value="/resources/images/${ player.getCharacter().getImage() }" htmlEscape="true" var="character" /> 
					<img style="float: left; width: 80px;" title="" src="${character}" id="character" />

				</td>
				<td>
					<c:choose>
						<c:when test="${ player.getRol().toString().equals('SHOGUN') }">
							<spring:url value="/resources/images/roles/${ player.getRol() }.png" htmlEscape="true" var="Rol" /> 
							<img style="float: left; width: 80px;" title="" src="${Rol}" id="Rol" />
						</c:when>
						<c:otherwise>
							<spring:url value="/resources/images/roles/ninguno.png" htmlEscape="true" var="Rol" /> 
							<img style="float: left; width: 80px;" title="" src="${Rol}" id="Rol" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</div>
</c:forEach>
	</div>

	<!-- BUTTONS -->
	<div class="buttons container">
		<form:form action="/game/end-turn">
            <input type="hidden" name="gameId" value="${ game.id }"></input>
            <button id="btn-end-turn" class="button"> END TURN </button>
        </form:form>
	</div>

</div>

<div class="row">

	<!-- DECK -->
	<div class="deck container">



                <div style="border-radius: 10px; background-color: #DFDADA">
                    <p style=" padding: 5px 10px 1px 50px;"> DECK</p>
                    <p style=" padding: 1px 1px 10px 50px;color:red;">(${deck.size()})</p>
                    <img src="/resources/images/roles/ninguno.png" alt="SHOGUN" style="width: 35%; height: 35%;padding:5px 35px ;" />

                    <form:form action="/game/steal">
                                                <input type="hidden" name="gameId" value="${ game.id }"></input>
                                                <!--  BOTÃ¯Â¿Â½N PARA ROBAR
                                                <c:if test="${game.currentPlayer==POVplayer.getUser().username}">
                                                <button class="btn btn-default" type="submit" style="padding:5px 25px">Select</button>
                                                </c:if>
                                                -->
                                </form:form>
                </div>
                <div style="border-radius: 10px; background-color: #DFDADA">
                    <p style=" padding: 5px 10px 1px 50px;"> DISCARD </p>
                    <p style=" padding: 1px 1px 10px 50px;color:red;">(${discardPile.size()})</p>
                    <img src="/resources/images/roles/ninguno.png" alt="SHOGUN" style="width: 35%; height: 35%;  padding: 5px 35px" />

                </div>



    </div>

	<!-- BLANK SPACE -->
	<div class="blank-space container">
	
	
                        <c:if test="${ game.currentPlayer.getUser().getUsername().equals(POVplayer.getUser().getUsername()) }">
                  <c:if test="${game.getGamePhase().equals(GamePhase.ATTACK) }">
                        <div id="card">
	<p>ATTACK CARD</p>
		 <img src="/resources/images/cards/${game.getUseCard().getName()}.png" alt="card" style="height: 70%; width:auto" />
	
	</div></c:if>
                                <c:if test="${ game.playersInRange.size()!=0 }">
                                
                                
                                
								<c:forEach items="${ game.playersInRange }" var ="player" varStatus="loop">
							
            <div id="select" >                
                
                           
                   <form:form class="form-horizontal"
							action="/game/selectPlayer/${game.id}/${player.getUser().getUsername()}"
							id="edit-user-form">
							  <c:if test="${game.getGamePhase().equals(GamePhase.ATTACK) }">
							<br><br>
							<button class="btn btn-default" type="submit">${player.getUser().getUsername()}</button></c:if>
							
							</form:form> 
			  </div>
			  
			  
			  
			  
			  
								</c:forEach>
								
								</c:if>
								 
								 </c:if>
								 
			<c:if test="${game.getGamePhase().equals(GamePhase.PARADA) && game.getAttackerPlayer().equals(POVplayer)}">
			<div id="card">
			<p>ATTACK CARD</p>
		 <img src="/resources/images/cards/${game.getUseCard().getName()}.png" alt="card" style="height: 70%; width:auto" />
	
	</div>
			<p>Estas siendo atacado. ¿Quieres usar una parada?</p>
			 <form:form class="form-horizontal"
							action="/game/parada/${game.id}/${game.getAttackerPlayer()}"
							id="edit-user-form">
							
							<br><br>
							<button class="btn btn-default" type="submit">SI</button>
							
							</form:form>
							
			<form:form class="form-horizontal"
							action="/game/attack/${game.id}/${game.getAttackerPlayer()}"
							id="edit-user-form">
							
							<br><br>
							<button class="btn btn-default" type="submit">NO</button>
							
							</form:form>  
			</c:if>
			
			
			<c:if test="${game.getGamePhase().equals(GamePhase.GRITODEBATALLA) && (!(game.getCurrentPlayer().equals(POVplayer))) && game.waitingForPlayer.contains(POVplayer)}">
			<p>Han usado Grito de batalla. ¿Que quieres hacer?</p>
			 <form:form class="form-horizontal"
							action="/game/choose1/${game.id}/${POVplayer}"
							id="edit-user-form">
							
							<br><br>
							<button class="btn btn-default" type="submit">Sufrir 1 de daño</button>
							
							</form:form>
							
							
			<c:if test="${POVplayer.getHaveParada()==true}">				
			<form:form class="form-horizontal"
							action="/game/choose2/${game.id}/${POVplayer}"
							id="edit-user-form">
							
							<br><br>
							<button class="btn btn-default" type="submit">Descartar parada</button>
							
							</form:form>  </c:if>
			</c:if>
			
			<c:if test="${game.getGamePhase().equals(GamePhase.JIUJITSU) && (!(game.getCurrentPlayer().equals(POVplayer))) && game.waitingForPlayer.contains(POVplayer)}">
			<p>Han usado JIU-JITSU. ¿Que quieres hacer?</p>
			 <form:form class="form-horizontal"
							action="/game/choose11/${game.id}/${POVplayer}"
							id="edit-user-form">
							
							<br><br>
							<button class="btn btn-default" type="submit">Sufrir 1 de daño</button>
							
							</form:form>
							
			<c:if test="${POVplayer.getHaveRedCard()==true}">			
						
			<form:form class="form-horizontal"
							action="/game/choose21/${game.id}/${POVplayer}"
							id="edit-user-form">
							
							<br><br>
							<button class="btn btn-default" type="submit">Descartar 1 arma</button>
							
							</form:form>  </c:if></c:if>
			
			<c:if test="${game.getGamePhase().equals(GamePhase.DISCARDARM) && (!(game.getCurrentPlayer().equals(POVplayer))) && game.waitingForPlayer.contains(POVplayer) && game.getPlayerChoose().equals(POVplayer)}">
			<p>¿Que arma quieres descartar?</p>
			<c:forEach items="${game.getListJiuJitsu()}" var ="cards" varStatus="loop">
		
			<form:form class="form-horizontal"
							action="/game/deleteCard/${cards.getName()}/${game.id}/${POVplayer}"
							id="edit-user-form">
							
							
							<button class="btn btn-default" type="submit">${cards.getName()}</button>
							
							</form:form>
			</c:forEach>
			</c:if>
			
			<c:if test="${game.getGamePhase().equals(GamePhase.DISCARDARM) && (!(game.getCurrentPlayer().equals(POVplayer))) && game.waitingForPlayer.contains(POVplayer) && (!(game.getPlayerChoose().equals(POVplayer)))}">
			<p>EL JUGADOR ${ game.getPlayerChoose()} ESTÁ DESCARTANDO UN ARMA. POR FAVOR ESPERE</p>
			</c:if>
			
			<c:if test="${game.getGamePhase().equals(GamePhase.GRITODEBATALLA) && game.getCurrentPlayer().equals(POVplayer)}">
			<div align="center">
			<img src="/resources/images/reloj.gif" alt="card" style="height: 70%; width:auto" />
			<p>${game.waitingForPlayer.size()} JUGADORES ESTAN DECIDIENDO QUE HACER</p>
			</div>
			</c:if>											
							                                    
			<c:if test="${(game.getGamePhase().equals(GamePhase.JIUJITSU) || (game.getGamePhase().equals(GamePhase.DISCARDARM))) && game.getCurrentPlayer().equals(POVplayer)}">
			<div align="center">
			<img src="/resources/images/reloj.gif" alt="card" style="height: 70%; width:auto" />
		
			<p>${game.waitingForPlayer.size()} JUGADORES ESTAN DECIDIENDO QUE HACER</p>
			</div>
			</c:if>	
			
			<c:if test="${game.getGamePhase().equals(GamePhase.PARADA)  && game.getCurrentPlayer().equals(POVplayer)}">
			<div align="center">
			<img src="/resources/images/reloj.gif" alt="card" style="height: 70%; width:auto" />
			<p>EL JUGADOR QUE HAS ATACADO TIENE PARADA Y ESTA DECIDIENDO QUE HACER</p>
			</div>
			</c:if>
	</div>

	<!-- YOUR PLAYER INFO -->
	<div class="your-player container">
	
	<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
			    		<c:if test="${ player.getUser().getUsername().equals(POVplayer.getUser().getUsername()) }">
			    		<div>
			    		
			    				
			    				<div id="div2" style="display: inline-block; border-radius: 50px">
			    				<h2>TU PERSONAJE</h2>
			    						<img src="/resources/images/${ player.getCharacter().getImage() }" alt="character" style="height: 40%; width:auto" />
			    						<p>${player.getCharacter().getName()}</p>
			    				</div>
			    				
			    				<div id="div1" style="display: inline-block; border-radius: 50px">
			    			<h2>TU ROL</h2>
			    			<img src="/resources/images/roles/${player.getRol()}.png" alt="charactere" style="height: 40%; width:auto" />
			    				<p> ${player.getRol()}</p> 
			    				</div>
			    				</div>
			    				
			    			
			    			
			    		</c:if>
			    </c:forEach>	</div>

</div>

<div class="row">

	<!-- DISCARD -->
	<div class="discard container"></div>

	<!-- YOUR EQUIPMENT -->
	<div class="equipment container">
	<table>
	<th>Tu equipamiento:</th>
	
	<tr>
	<td >
					<spring:url value="/resources/images/cards/armadura.png" htmlEscape="true" var="equipment1" /> 
					<c:choose>
						<c:when test="${ POVplayer.getDistanceBonus()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment1}" id="equipment1" /> x${ POVplayer.getDistanceBonus() }</p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment1}" id="equipment1" /> x${ POVplayer.getDistanceBonus() }</p>
						</c:otherwise>
					</c:choose>
					
					
				</td>
				<td>
					<spring:url value="/resources/images/cards/desenvainado rapido.png" htmlEscape="true" var="equipment2" />
					<c:choose>
						<c:when test="${ POVplayer.getDamageBonus()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment2}" id="equipment2" /> x${ POVplayer.getDamageBonus() } </p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment2}" id="equipment2" /> x${ POVplayer.getDamageBonus() } </p>
						</c:otherwise>
					</c:choose> 
					
				</td>
				<td>
					<spring:url value="/resources/images/cards/concentracion.png" htmlEscape="true" var="equipment3" /> 
					<c:choose>
						<c:when test="${POVplayer.getWeaponBonus()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment3}" id="equipment3" /> x${ POVplayer.getWeaponBonus() } </p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment3}" id="equipment3" /> x${ POVplayer.getWeaponBonus() } </p>
						</c:otherwise>
					</c:choose>
					
				</td>
				</tr>
				</table>
				
							</div>
	</div>

<div class="row">

	<!-- HAND -->
	<div class="hand container">
	<div  style="background-color: #DFDADA; border-radius:15px; justify-self:end;">
                    <div style="max-width:98%; display:inline">
                    <c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
                        <c:if test="${ player.getUser().getUsername().equals(POVplayer.getUser().getUsername()) }">
							<form:form action="/game/use-card">
								<input type="hidden" name="gameId" value="${ game.id }"></input>
								<c:forEach items="${ player.hand }" var ="card" varStatus="loop">
                                    <div style="display: inline-block; height:auto; width:12%">
                                        <img style="height:auto; width:100%" src="/resources/images/cards/${card.name}.png" alt="${card.name}"/>
                                        <input type="radio" value="${card.name}" name="cardName" />
                                    </div>
								</c:forEach>
								<button id="btn-end-turn" class="button"> USAR CARTA </button>
							</form:form>
                          </c:if>
                    </c:forEach>
                    </div>
    </div>
	</div>

</div>

<script>

function myFunction(pl) {
    for (let el of document.querySelectorAll('.division')) el.style.display = 'none';
    if(pl.style.display == "inline"){

        pl.style.display = "none";
    }
    else{
        pl.style.display = "inline";
    }
}

</script>