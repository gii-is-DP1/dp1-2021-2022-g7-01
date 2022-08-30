<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="samuraisword.game.GamePhase"%>

<script type="text/javascript">
setTimeout(function() {
	  location.reload();
	}, 6000);
</script>

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
	display: flex;
	
}

.error {
	width: 400px;
	height: 200px;
	background-color: yellow;
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
}

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

.your-player-info-container {
	display: inline-block;
	width:50%;
	margin: 0;
	padding: 0;
	display: flex;
	align-items: center;
	flex-direction: column;
}
.your-player-title {
	margin: 0;
}
.your-player-image {
	height:180px;
}
#card {margin: 8px; height:200px; width:27%; float:left}
#select {float: right; margin: 5px}
#dano1 {margin: 10px; float: left; }
#dano2 {float: right; margin: 5px}
#but {font-size: 10px;}
#sel {float: left; margin: 5px} 
#mes {float: left;  height: 180px; width:70%; margin-left: 10px} 
#s {float: right; margin: 50px}

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
<c:set value="${game.error}" var="error" />

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
				    		<button class = "player" style="border-radius: 10px; background-color: yellow;" onclick="myFunction(${player.getUser().getUsername()})"> ${ player.getUser().getUsername() } </button>
				    	</c:when>
				    	<c:when test="${ player.getUser().getUsername().equals(POVplayer.getUser().getUsername()) }">
				    		<button class = "player" style="border-radius: 10px; background-color: #40CFFF;" onclick="myFunction(${player.getUser().getUsername()})"> ${ player.getUser().getUsername() } </button>
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
					<th>
						<c:if test="${ player.getNBushido()>0 }">
							<h2 style ="color:#f2f2f2" >Tiene Bushido</h2>
						</c:if>
					</th>
				</tr>
				<tr>
					<td >
						<spring:url value="/resources/images/cards/armadura.png" htmlEscape="true" var="equipment1" /> 
						<c:choose>
							<c:when test="${ player.getNArmor()==0 }">
								<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment1}" id="equipment1" /> x${ player.getNArmor() }</p>
							</c:when>
							<c:otherwise>
								<p><img style="float: left; width: 80px;" title="" src="${equipment1}" id="equipment1" /> x${ player.getNArmor() }</p>
							</c:otherwise>
						</c:choose>
						
						
					</td>
					<td>
						<spring:url value="/resources/images/cards/desenvainado rapido.png" htmlEscape="true" var="equipment2" />
						<c:choose>
							<c:when test="${ player.getNFastDraw()==0 }">
								<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment2}" id="equipment2" /> x${ player.getNFastDraw() } </p>
							</c:when>
							<c:otherwise>
								<p><img style="float: left; width: 80px;" title="" src="${equipment2}" id="equipment2" /> x${ player.getNFastDraw() } </p>
							</c:otherwise>
						</c:choose> 
						
					</td>
					<td>
						<spring:url value="/resources/images/cards/concentracion.png" htmlEscape="true" var="equipment3" /> 
						<c:choose>
							<c:when test="${ player.getNFocus()==0 }">
								<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment3}" id="equipment3" /> x${ player.getNFocus() } </p>
							</c:when>
							<c:otherwise>
								<p><img style="float: left; width: 80px;" title="" src="${equipment3}" id="equipment3" /> x${ player.getNFocus() } </p>
	
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
	<c:if test="${POVplayer.equals(game.currentPlayer)}">
		<c:choose>
			<c:when test="${gameStatus=='MAIN'}">
					<form:form action="/game/end-turn">
			            <input type="hidden" name="gameId" value="${ game.id }"></input>
			            <button id="btn-end-turn" class="button"> END TURN </button>
			        </form:form>
			</c:when>
			<c:when test="${gameStatus=='DISCARDOTHER'}">
				<button id="btn-end-turn" class="button" form="discardForm"> DESCARTAR CARTA </button>
			</c:when>
		</c:choose>
	</c:if>
	</div>

</div>

<div class="row">

	<!-- DECK -->
	<div class="deck container">



                <div style="border-radius: 10px; background-color: #DFDADA">
                    <p style=" padding: 5px 10px 1px 50px;"> DECK</p>
                    <p style=" padding: 1px 1px 10px 50px;color:red;">(${deck.size()})</p>
                    <img src="/resources/images/roles/ninguno.png" alt="SHOGUN" style="width: 35%; height: 35%;padding:5px 35px ;" />

                    <!-- <form:form action="/game/steal">
                                                <input type="hidden" name="gameId" value="${ game.id }"></input>
                                                  BOTÃ¯Â¿Â½N PARA ROBAR
                                                <c:if test="${game.currentPlayer==POVplayer.getUser().username}">
                                                <button class="btn btn-default" type="submit" style="padding:5px 25px">Select</button>
                                                </c:if>
                                               
                                </form:form> -->
                </div>
                <div style="border-radius: 10px; background-color: #DFDADA">
                    <p style=" padding: 5px 10px 1px 50px;"> DISCARD </p>
                    <p style=" padding: 1px 1px 10px 50px;color:red;">(${discardPile.size()})</p>
                    <img src="${discardImage}" alt="SHOGUN" style="width: 35%; height: 35%;  padding: 5px 35px" />

                </div>



    </div>

	<!-- BLANK SPACE -->
	<div class="blank-space container">	
                        <c:if test="${ game.currentPlayer.getUser().getUsername().equals(POVplayer.getUser().getUsername()) }">
                  <c:if test="${game.getGamePhase().equals(GamePhase.ATTACK) }">
                        <div id="card">
	<p>ATTACK CARD</p>
		 <img src="/resources/images/cards/${game.getUseCard().getName()}.png" alt="card" style="height: 70%; width:auto" />
	
	</div>
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
								 <div id="select" >                
                
                           
                   <form:form class="form-horizontal"
							action="/game/selectPlayer/${game.id}/none"
							id="edit-user-form">
							  <c:if test="${game.getGamePhase().equals(GamePhase.ATTACK) }">
							<br><br>
							<button class="btn btn-default" type="submit">Me he arrepentido</button></c:if>
							
							</form:form> 
			  </div>
								 </c:if>
			</c:if>					 
			<c:if test="${game.getGamePhase().equals(GamePhase.PARADA) && game.getAttackerPlayer().equals(POVplayer)}">
			<div id="card">
			<p>ATTACK CARD</p>
		 <img src="/resources/images/cards/${game.getUseCard().getName()}.png" alt="card" style="height: 70%; width:auto" />
	
	</div>
			<p>Estas siendo atacado. �Quieres usar una parada?</p>
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
			
			<c:if test="${game.getGamePhase().equals(GamePhase.AVISO) && game.getAttackerPlayer().equals(POVplayer)}">
			<div id="mes">
			<c:if test="${game.getCurrentPlayer().getDamageBonus()==0}">
			<p>${ game.getCurrentPlayer()} te ha atacado y te ha hecho ${game.getAttackerDamage()} de da�o</p></c:if>
			<c:if test="${game.getCurrentPlayer().getDamageBonus()>0}">
			<p>${ game.getCurrentPlayer()} te ha atacado y te ha hecho ${game.getAttackerDamage()} de da�o (Bonus de da�o incluido)</p></c:if>
		 <img src="/resources/images/cards/${game.getUseCard().getName()}.png" alt="card" style="height: 70%; width:auto" />
	
		</div>
		
		
		<form:form class="form-horizontal"
							action="/game/aviso/${game.id}"
							id="edit-user-form">
							
							<div id="s">
							<button class="btn btn-default" type="submit">OK</button></div>
							
							</form:form>
		
			</c:if>
			
			
			
			<c:if test="${game.getGamePhase().equals(GamePhase.GRITODEBATALLA) && (!(game.getCurrentPlayer().equals(POVplayer))) && game.waitingForPlayer.contains(POVplayer) && POVplayer.getIndefence()==false}">
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
			
			<c:if test="${game.getGamePhase().equals(GamePhase.JIUJITSU) && (!(game.getCurrentPlayer().equals(POVplayer))) && game.waitingForPlayer.contains(POVplayer) && POVplayer.getIndefence()==false}">
			<p>Han usado JIU-JITSU. ¿Que quieres hacer?</p>
			<div id="dano1">
			 <form:form class="form-horizontal"
							action="/game/choose11/${game.id}/${POVplayer}"
							id="edit-user-form">
							
							<br><br><div id="dano">
							<button class="btn btn-default" type="submit">Sufrir 1 de daño</button></div>
							
							</form:form></div>
							
			<c:if test="${POVplayer.getHaveRedCard()==true}">			
			<c:forEach items="${POVplayer.getHand()}" var ="hand" varStatus="loop">
			
			<form:form class="form-horizontal"
							action="/game/choose21/${hand.getName()}/${game.id}/${POVplayer}"
							id="edit-user-form">
							
							<c:if test="${hand.color=='Red'}"><div id="select" > 
							<button id="but" class="btn btn-default" type="submit">Descartar ${hand.getName() }</button></div></c:if>
							
							</form:form></c:forEach></c:if></c:if>
			
			
			
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
	
	    <c:if test="${ game.currentPlayer.getUser().getUsername().equals(POVplayer.getUser().getUsername()) }">
	    
        <c:if test="${game.getGamePhase().equals(GamePhase.DISCARDPLAYER) }">
    	<div id="card res">
    		<p>DISCARD SOME PLAYER</p>
         	<!--  <img src="/resources/images/cards/distraccion.png" alt="card" style="height: 70%; width:auto" /> -->
		</div>
    	
   		                      
       
                
					<c:forEach items="${ game.listPlayers }" var ="player" varStatus="loop">                  

                   <form:form class="form-horizontal"
                            action="/game/distraccion/${game.id}/${player.getUser().getUsername()}"
                            id="edit-user-form">
                              
							<div id="sel">
                            <button class="btn btn-default" type="submit">${player.getUser().getUsername()}</button></div>
                            </form:form> 
                    </c:forEach>
                            
       
        </c:if>                      
		<c:if test="${game.getGamePhase().equals(GamePhase.RESPIRACION) }">
    	<div id="card res">
    		<p>SELECT SOME PLAYER</p>
         	<!--  <img src="/resources/images/cards/distraccion.png" alt="card" style="height: 70%; width:auto" /> -->
		</div>
    	
   		                      
       
                
					<c:forEach items="${ game.listPlayers }" var ="player" varStatus="loop">
						<c:if test="${!player.equals(POVplayer)}">
                       
                   <form:form class="form-horizontal"
                            action="/game/respiracion/${game.id}/${player.getUser().getUsername()}"
                            id="edit-user-form">
                              
 							<div id="sel" >
                            <button class="btn btn-default" type="submit">${player.getUser().getUsername()}</button> </div>
                            </form:form> 
                            </c:if>
                    </c:forEach>
                            
       
                               
		</c:if>
		
		
        </c:if>
                             
		<c:if test="${POVplayer.equals(game.currentPlayer)}">
			<c:if test="${gameStatus=='DISCARDOTHER'}">
				<form:form action="/game/discard-other-card" id = "discardForm">
					<table>
						<tr>
							<h2>Selecciona a quién quieres descartarle una carta</h2>
							<input type="hidden" name="gameId" value="${ game.id }"></input>
							<c:forEach items="${listPlayer}" var ="player" varStatus="loop">
								<c:if test="${!player.getUser().getUsername().equals(POVplayer.getUser().getUsername())}">
								<input type="radio" value="${ player.getUser().getUsername() }" name="player" />
								<label for="${ player.getUser().getUsername() }">${player.getUser().getUsername()}</label>
								</c:if>
							</c:forEach>
						</tr>
						<br>
						<tr>
							<h2>Selecciona el objeto que le quieres quitar</h2>
							<input type="radio" value="hand" name="cardName"/>
							<label for="hand">mano</label>
							<input type="radio" value="armadura" name="cardName"/>
							<label for="armadura">armadura</label>
							<input type="radio" value="concentracion" name="cardName"/>
							<label for="concentracion">concentraci�n</label>
							<input type="radio" value="desenvainado rapido" name="cardName"/>
							<label for="desenvainado rapido">Desenvainado rapido</label>
						</tr>
					</table>
				</form:form>
			</c:if>
			<c:if test="${gameStatus=='BUSHIDO'}">
				<h2>Selecciona a quién quieres equiparle el bushido</h2>
				<table>
					<tr>
						<c:forEach items="${listPlayer}" var ="player" varStatus="loop">
							<c:if test="${!player.getUser().getUsername().equals(POVplayer.getUser().getUsername())}">
							<th>
								<form:form action="/game/equip-bushido" id = "discardForm">
									<input type="hidden" name="gameId" value="${ game.id }"></input>
									<input type="hidden" name="player" value="${ player.getUser().getUsername() }"></input>
									<button > ${ player.getUser().getUsername() } </button>
								</form:form>
							</th>
							</c:if>
						</c:forEach>
					</tr>
				</table>
			</c:if>
			<c:if test="${gameStatus=='DISCARTRED'}">
				<h2>Selecciona la carta que quieras eliminar por culpa del bushido</h2>
				<table>
					<tr>
						<c:forEach items="${POVplayer.getHand()}" var ="hand" varStatus="loop">
							<form:form action="/game/pass-bushido" id = "discardForm">
								<c:if test="${hand.color=='Red'}">
									<th>
									<input type="hidden" name="gameId" value="${ game.id }"></input>
									<input type="hidden" name="card" value="${ hand.getName() }"></input>
									<button> ${ hand.getName() } </button>
									</th>
								</c:if>
							</form:form>
						</c:forEach>
						<th>
						<form:form action="/game/pass-bushido" id = "discardForm">
							<input type="hidden" name="gameId" value="${ game.id }"></input>
							<input type="hidden" name="card" value="NONE"></input>
							<button> No descartar </button>
						</form:form>
						</th>
					</tr>
				</table>
			</c:if>
		</c:if>
	</div>

	<!-- YOUR PLAYER INFO -->
	<div class="your-player container">
	
	<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
			    		<c:if test="${ player.getUser().getUsername().equals(POVplayer.getUser().getUsername()) }">
			    		
			    		
			    				
			    				<div class="your-player-info-container">
			    				<h4 class="your-player-title">TU PERSONAJE</h4>
			    						<img class="your-player-image" src="/resources/images/${ player.getCharacter().getImage() }" alt="character" />
			    				</div>
			    				
			    				<div class="your-player-info-container">
			    			<h4 class="your-player-title">TU ROL</h4>
			    			<img class="your-player-image" src="/resources/images/roles/${player.getRol()}.png" alt="charactere" />
			    				</div>
			    				
			    			
			    			
			    		</c:if>
			    </c:forEach>	</div>

</div>

<div class="row">

	<!-- DISCARD -->
	<div class="discard container">
	<h2 style ="color:#f2f2f2" >N�mero de ataques restantes: ${ POVplayer.getWeaponBonus() }</h2>
	<h2 style ="color:#f2f2f2" >Aumento de da�o: +${ POVplayer.getDamageBonus() }</h2>
	<h2 style ="color:#f2f2f2" >Distancia a la que te ven: +${ POVplayer.getDistanceBonus() }</h2>
	
	</div>

	<!-- YOUR EQUIPMENT -->
	<div class="equipment container">
	<table>
	<th>Tu equipamiento:</th>
	
	<tr>
	<td >
					<spring:url value="/resources/images/cards/armadura.png" htmlEscape="true" var="equipment1" /> 
					<c:choose>
						<c:when test="${ POVplayer.getNArmor()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment1}" id="equipment1" /> x${ POVplayer.getNArmor() }</p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment1}" id="equipment1" /> x${ POVplayer.getNArmor() }</p>
						</c:otherwise>
					</c:choose>
					
					
				</td>
				<td>
					<spring:url value="/resources/images/cards/desenvainado rapido.png" htmlEscape="true" var="equipment2" />
					<c:choose>
						<c:when test="${ POVplayer.getNFastDraw()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment2}" id="equipment2" /> x${ POVplayer.getNFastDraw() } </p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment2}" id="equipment2" /> x${ POVplayer.getNFastDraw() } </p>
						</c:otherwise>
					</c:choose> 
					
				</td>
				<td>
					<spring:url value="/resources/images/cards/concentracion.png" htmlEscape="true" var="equipment3" /> 
					<c:choose>
						<c:when test="${POVplayer.getNFocus()==0 }">
							<p><img style="float: left; width: 80px; opacity: 0.5;" title="" src="${equipment3}" id="equipment3" /> x${ POVplayer.getNFocus() } </p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 80px;" title="" src="${equipment3}" id="equipment3" /> x${ POVplayer.getNFocus() } </p>
						</c:otherwise>
					</c:choose>
					
				</td>
				<td>
					<c:if test="${POVplayer.getNBushido()>0 }">
						<spring:url value="/resources/images/cards/bushido.png" htmlEscape="true" var="equipment4" /> 
						<p><img style="float: left; width: 80px;" title="" src="${equipment4}" id="equipment4" /></p>
					</c:if>
				</td>
				</tr>
				</table>
				
							</div>
	<div class="error container">
		<c:if test="${POVplayer.equals(game.currentPlayer)}">
		<p>${error}</p>
		</c:if>
	</div>
	</div>
<div class="row">

	<!-- HAND -->
	<div class="hand container">
	<div  style="width:100%; background-color: #DFDADA; border-radius:15px; justify-self:end;">
		<div style="width:98%; display:inline">
        	<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
            	<c:if test="${ player.getUser().getUsername().equals(POVplayer.getUser().getUsername())}">
            		<c:choose>
						<c:when test="${gameStatus=='DISCARD'}">
							<form:form action="/game/discard-hand-card" style="width: 100%">
								<input type="hidden" name="gameId" value="${ game.id }"></input>
								<c:forEach items="${ player.hand }" var ="card" varStatus="loop">
                                    <div style="display: inline-block; height:auto; width:12%">
                                        <img style="height:auto; width:100%" src="/resources/images/cards/${card.name}.png" alt="${card.name}"/>
                                        <input type="radio" value="${card.name}" name="cardName" />
                                    </div>
								</c:forEach>
								<input type="radio" value="no-selected" name="cardName" checked style="display: none" />
								<c:if test="${POVplayer.hand.size()>0}">
									<button id="btn-end-turn" class="button"> DESCARTAR CARTA </button>
								</c:if>
							</form:form>
						</c:when>
						<c:when test="${gameStatus=='MAIN'}">
							<form:form action="/game/use-card" style="width: 100%">
								<input type="hidden" name="gameId" value="${ game.id }"></input>
								<c:forEach items="${ player.hand }" var ="card" varStatus="loop">
                                    <div style="display: inline-block; height:auto; width:12%">
                                        <img style="height:auto; width:100%" src="/resources/images/cards/${card.name}.png" alt="${card.name}"/>
                                        <input type="radio" value="${card.name}" name="cardName" />
                                    </div>
								</c:forEach>
								<input type="radio" value="no-selected" name="cardName" checked style="display: none" />
								<c:if test="${POVplayer.equals(game.currentPlayer)}">
									<c:if test="${POVplayer.hand.size()>0}">
										<button id="btn-end-turn" class="button"> USAR CARTA </button>
									</c:if>
								
								</c:if>
							</form:form>
						</c:when>
						<c:otherwise>
							<c:forEach items="${ player.hand }" var ="card" varStatus="loop">
                                   <div style="display: inline-block; height:auto; width:12%">
                                       <img style="height:auto; width:100%" src="/resources/images/cards/${card.name}.png" alt="${card.name}"/>
                                   </div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
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