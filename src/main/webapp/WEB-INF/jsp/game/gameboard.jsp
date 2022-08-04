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
	background-color: #E5E4E2;
}

.button:active {
  background-color: #E5E4E2;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
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




	<div  style="background-color: #DFDADA; border-radius:15px; width:60%; height: auto">
					<div style="max-width:98%; display:inline">
					<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
						<c:if test="${ player.getUser().getUsername().equals(POVplayer.getUsername()) }">
							<form action="ACTION AQUI">
								<c:forEach items="${ player.hand }" var ="card" varStatus="loop">
				    				<div style="display: inline-block; height:auto; width:12%">
				    					<img style="height:auto; width:100%" src="/resources/images/cards/${card.name}.png" alt="${card.name}"/>
				    					<input type="radio" value="${card.name}" name="selectedCard" />
				    				</div>			
				  				</c:forEach>
				  				<button type="submit" class="button" style="max-width: 10%; max-height:10%; ">USAR CARTA</button>
				  			</form>
				  		</c:if>	
					</c:forEach>
					</div>
	</div>


















