<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="samuraisword.game.GamePhase" %>

<style>

body{
	background-image: url("/resources/images/wood2.jpg");
	background-position: center;
	background-size: cover;
 	background-opacity: 50%;
  	padding-top: 50px;
  	justify-content: center;
  	background-height: 100%;
	
}

#border{
	 border: 2px dotted blue;
}
/*tenemos un div central (main) circular alrededor del cual estaran posicionados los divs que renderizen la vista de cada usuario*/
#main {
	text-align:center;
	margin-right: 5%;
    position: relative;
    border-radius: 50%;
    height: 100%;
    width: 100%;
}
/*divs de jugadores que orbitan el central (main)*/
.circle {
    position: absolute;
    left: calc(50% - 25px);
    top: calc(50% - 25px);
    height: 100px;
    width: 150px;
    margin: 0 auto;
}
/*Angulo por defecto (separacion angular con 4 jugadores)*/
:root {
	--angle : 90deg;
}

/*el primero no lo desplazamos*/
.circle:nth-child(1n) {
    transform: translateX(350px);
    position: absolute;
}
/*desplazamos el resto de jugadores; cada cual más lejos con respecto al primero*/
.circle:nth-child(2n) {
    transform: rotate(calc(var(--angle))) translateX(350px);
    position: absolute;
}
.circle:nth-child(3n) {
    transform: rotate(calc(var(--angle)*2)) translateX(350px);
}
.circle:nth-child(4n) {
    transform: rotate(calc(var(--angle)*3)) translateX(350px);
}
.circle:nth-child(5n) {
    transform: rotate(calc(var(--angle)*4)) translateX(350px);
}
.circle:nth-child(6n) {
    transform: rotate(calc(var(--angle)*5)) translateX(350px);
}
.circle:nth-child(7n) {
    transform: rotate(calc(var(--angle)*6)) translateX(350px);
    position: absolute;
}

.innerCircle {
	-ms-transform: translateY(-50%); 
	transform: translateY(-50%);
}

.button{
	width:100%;
	height: 25%;
	border-radius: 15px;
	justify-content: center;
	cursor: pointer;
  	outline: none;
	border: none;
    color: black;
    box-shadow: 0 9px #999;
}

.button:hover{
	background-color: #3e8e41;
}

.button:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}

.button:nth-child(1n){
	background-color: blue;
	margin-bottom: 3%;
}
.button:nth-child(2n){
	background-color: yellow;
	margin-bottom: 3%;
}

.button:nth-child(3n){
	background-color: red;
	margin-bottom: 3%;
}

#btn-end-turn{
	background-color: gray;
	margin-bottom: 3%;
}

.img-wrap{
	position: relative;
	height: 160px;
}

.img-description {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: #FCA5A5;
  color: black;
  visibility: hidden;
  opacity: 0;
  transition: opacity .2s, visibility .2s;
}

.img-wrap:hover .img-description {
  visibility: visible;
  opacity: 1;
  }
  
.foeHand{
	visibility: hidden;
  	opacity: 0;
  	transition: opacity .2s, visibility .2s;
}
  
.viewEquiped{
	width:100%;
	height: 30%;
	border-radius: 15px;
	justify-content: center;
	cursor: pointer;
  	outline: none;
	border: none;
    color: black;
    box-shadow: 0 9px #999;
}

.viewEquiped:hover{
	background-color: #3e8e41;
}

.viewEquiped:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);

}

.viewEquiped:active .foeHand{
  visibility: visible;
  opacity: 1;
}


.viewAttackCards{
	visibility: hidden;
  	opacity: 0;
  	transition: opacity .2s, visibility .2s;
}

.button:nth-child(3n):focus .viewAttackCards{
  visibility: visible;
  opacity: 1;
}


  

}

</style>

<c:set value="${game.listPlayers}" var="listPlayer" />
<c:set value="${game.deck}" var="deck" />
<c:set value="${game.discardPile}" var="discardPile" />
<c:set value="${game.currentPlayer.user}" var="currentUser" />


<!-- EN CASO DE QUE NO SEAN 4 JUGADORES REAJUSTAMOS EL ANGULO DE SEPARACION QUE SERA DADO POR 360/Nºjugadores -->

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



<div style="text-align:center;" >
	<div style="margin: auto;" >
			<div style="display: inline-block; color: black; vertical-align:top; width: 10%; height: 60%;"> 
				<div style="border-radius: 10px; background-color: #DFDADA">
					<p> DECK (${deck.size()})</p>
					<img src="/resources/images/roles/ninguno.png" alt="SHOGUN" style="width: 50%; height: auto" />
				</div>
				<div style="border-radius: 10px; background-color: #DFDADA">
					<p> DISCARD PILE (${discardPile.size()})</p>
					<img src="/resources/images/roles/ninguno.png" alt="SHOGUN" style="width: 50%; height: auto" />
				</div>
			</div>
		    <div style="display: inline-block; width: 55%; height: 100%; padding-top: 20px;text-align:center; vertical-align: top; margin-right: 50px " >
			    <div id="main">
			    	<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
			    		<div class="circle"> 
			    			<!-- div intermediario para corregir la rotacion dentro -->
			    			<div style="transform: rotate(calc(360deg - var(--angle)* ${ loop.index })">
			    			<div style="border-radius: 10px; background-color: #DFDADA">
			    				<c:if test="${ player.getRol().toString().equals('SHOGUN') }">
			    				 <img src="/resources/images/shogun.png" alt="SHOGUN" style="width: 25%; height: auto" />	
			    				</c:if>
			    				<c:if test="${ game.currentPlayer.equals(player) }">
									<span>Current player</span>
								</c:if>
			    				<p>${player.getUser().getUsername()}</p>
								<div>
									<div style="display: inline-block;" class="img-wrap">
			    						<img src="/resources/images/${ player.getCharacter().getImage() }" alt="character" style="height: 90%; max-height:100%; max-width:100%;" />
			    						<p class="img-description"> ${ player.getCharacter().getText() } </p>
			    					</div>
					    			<div style="display: inline-block;">
					    					<img src="/resources/images/honorLive/live.png" alt="live" style="width: 25%; height: auto; display: inline-block;" />
					    					<p style="display: inline-block;"> ${ player.getCurrentHearts() } </p>
					    				
					    					<img src="/resources/images/honorLive/honor.png" alt="live" style="width: 25%; height: auto" />	
					    					<p style="display: inline-block;"> ${ player.getHonor() } </p>	
					    			</div>
					    			<div class= "viewEquiped">
					    				View equipped cards
					    				<div class="foeHand" style="border-radius: 10px; border: solid black; background-color: #DFDADA; height: auto; width:auto; min-width: 50px; min-height:100px">
					    					<c:forEach items="${ player.equipment }" var ="card" varStatus="loop">
				    							<img style="height:120px; width:auto;" src="/resources/images/cards/${card.name}.png" alt="card"/>				    			
				  							</c:forEach>
				  							<c:if test="${ player.equipment.size()==0 }"> NONE EQUIPPED </c:if>
					    				</div>
					    			</div>
					    			
								</div>
			    			</div>
							<c:if test="${inRange.contains(player)}">
								<form:form action="/game/attack/playerselected">
					    						<input type="hidden" name="gameId" value="${ game.id }"></input>
					    						<input type="hidden" name="objectivePlayer" value="${ player }"></input>
					    						<input type="hidden" name="cardName" value="${ attackWeapon.name }"></input>
					    						<button class="btn btn-default" type="submit">Select</button>
					    		</form:form>
							</c:if>
							</div>
			    		</div>
			    	</c:forEach>
			    </div>
    		</div> 
    		
    		<div style="display: inline-block; width: 25%; height: 50%">
    			<div style="display: inline-block; width: 45%; height: 50%; text-align:center; vertical-align: top; margin-right:10px; ">
    			
					<button class="button"> EQUIP CARD </button>
					
					<button class="button"> USE CARD </button>
					
					
					
					<button class="button"> ATTACK </button>
					

					<form:form action="/game/end-turn">
						<input type="hidden" name="gameId" value="${ game.id }"></input>
						<button id="btn-end-turn" class="button"> END TURN </button>
					</form:form>

					
				</div>
				<div  style="display: inline-block; width: 45%; height: 50%; text-align:center; vertical-align: top">
					<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
			    		<c:if test="${ player.getUser().getUsername().equals(POVplayer.getUsername()) }">
			    			<div style="display: inline-block; border-radius: 10px; background-color: #DFDADA">
			    				<p> ${ player.getUser().getUsername() } (${player.getRol()})</p>
			    				<div style="display: inline-block;" class="img-wrap">
			    						<img src="/resources/images/${ player.getCharacter().getImage() }" alt="character" style="height: 100%; width:auto" />
			    						<p class="img-description"> ${ player.getCharacter().getText() } </p>
			    				</div>
			    				<div style="display: inline-block;">
			    				
			    					<img src="/resources/images/honorLive/live.png" alt="live" style="width: 10%; height: auto; display: inline-block;" />
			    					<p style="display: inline-block;"> ${ player.getCurrentHearts() } </p>
			    				
			    					<img src="/resources/images/honorLive/honor.png" alt="live" style="width: 10%; height: auto" />
			    					<p style="display: inline-block;"> ${ player.getHonor() } </p>	
			    			</div>
			    			</div>
			    			
			    		</c:if>
			    	</c:forEach>			
				</div>
				<br>
				<p style="color: white; padding-top: 20px">TU MANO</p>
				<div  style=" height: 60%; padding-top: 10px; margin-top: 10px; background-color: #DFDADA; border-radius:15px;">
					<div style="max-width:90%">
					<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
						<c:if test="${ player.getUser().getUsername().equals(POVplayer.getUsername()) }">
							<c:forEach items="${ player.hand }" var ="card" varStatus="loop">
				    			<div style="display: inline-block; height:auto; width:20%">
				    				<img style="height:auto; width:100%" src="/resources/images/cards/${card.name}.png" alt="${card.name}"/>	
					    			<c:if test="${currentUser.username.equals(POVplayer.username)}">
					    			<c:if test="${ card.color.equals('Red') }">
					    					<div>
					    						<form:form action="/game/attack/selectplayer">
						    						<input type="hidden" name="gameId" value="${ game.id }"></input>
						    						<input type="hidden" name="cardName" value="${ card.name }"></input>
						    						<input type="hidden" name="attackerPlayer" value="${ game.currentPlayer }"></input>
						    						<button class="btn btn-default" type="submit">Select</button>
						    					</form:form>
					    					</div>
					    			</c:if>
					    			<c:if test="${game.gamePhase == GamePhase.MAIN && (card.name=='armadura' || card.name=='concentracion' || card.name=='desenvainado rapido')}">
					    			    <form:form action="/game/select">
						    				<input type="hidden" name="gameId" value="${game.id}"></input>
											<input type="hidden" name="cardName" value="${card.name}"></input>
											<button id="btn-equip-card2" class="btn btn-default"> EQUIP </button>
										</form:form>
									</c:if>	
									<c:if test="${game.gamePhase == GamePhase.DISCARD}">
				    			
				    			
				    			<form:form action="/game/discard-card">
				    			<input type="hidden" name="gameId" value="${game.id}"></input>
								<input type="hidden" name="cardName" value="${card.name}"></input>
								<button id="btn-discard-card" class="btn btn-default"> DISCARD CARD </button>
								</form:form>
											
										</c:if>	
										</c:if>
				    			</div>
				    					    			
				  			</c:forEach>
				  			
				  		</c:if>	
					</c:forEach>
					</div>
				</div>	
				<p style="color: white">EQUIPADAS</p>
				<div  style=" height: 60%; padding-top: 10px; margin-top: 10px; background-color: #DFDADA; border-radius:15px;">
					<div style="display:inline-block; max-width:90%">

						<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
						<c:if test="${ player.getUser().getUsername().equals(POVplayer.getUsername()) }">
							<c:forEach items="${ player.equipment }" var ="card" varStatus="loop">
				    			<img style="height:auto; width:20%;" src="/resources/images/cards/${card.name}.png" alt="card"/>				    			
				  			</c:forEach>
				  		</c:if>	
					</c:forEach>

					</div>
					
				</div>		
    		</div>
	</div>
</div>


















