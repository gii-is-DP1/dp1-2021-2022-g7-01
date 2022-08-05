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
  	justify-content: center;
  	background-height: 100%;
	
}

/*tenemos un div central (main) circular alrededor del cual estaran posicionados los divs que renderizen la vista de cada usuario*/
#main {
	text-align:center;
	margin-right: 5%;
    position: relative;
    border-radius: 25%;
    height: 100%;
    width: 100%;
}
/*divs de jugadores que orbitan el central (main)*/
.circle {
    position: absolute;
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
.circle:nth-child(1n) {
    transform: translateX(80px);
    position: absolute;
}
/*desplazamos el resto de jugadores; cada cual más lejos con respecto al primero*/
.circle:nth-child(2n) {
    transform: rotate(calc(var(--angle))) translateX(80px);
    position: absolute;
}
.circle:nth-child(3n) {
    transform: rotate(calc(var(--angle)*2)) translateX(80px);
}
.circle:nth-child(4n) {
    transform: rotate(calc(var(--angle)*3)) translateX(80px);
}
.circle:nth-child(5n) {
    transform: rotate(calc(var(--angle)*4)) translateX(80px);
}
.circle:nth-child(6n) {
    transform: rotate(calc(var(--angle)*5)) translateX(80px);
}
.circle:nth-child(7n) {
    transform: rotate(calc(var(--angle)*6)) translateX(80px);
    position: absolute;
}

.innerCircle {
	-ms-transform: translateY(-50%); 
	transform: translateY(-50%);
}

.img-wrap{
	position: relative;
	height: 100px;
}

/*.circle:active {
    position: absolute;
    left: calc(15% - 25px);
    top: calc(10% - 25px);
    
    width: 50px;
    margin: 0 auto;
}
*/

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
#btn-end-turn{
	background-color: gray;
	margin-bottom: 3%;
}

.division{
	display: none;
}

table {
	width: 75%;
	border-collapse: collapse;
	top: 20px;
	left: 250px;
	position: absolute;
}
        
#equipment{
background-color: silver;
position: relative;
bottom: 20px;
left: 350px;
align-content: center;
padding-left: 300px;
outline-style: solid;
outline-color: black;
outline-width: 10px;
padding: 20px; 
width: 380px;
height: 120px;
overflow: auto;
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

<!-- ----------------------------------------------------------------------------------- -->


<div style="display: inline-block; width: 60%; padding-top: 150px;text-align:center; vertical-align: top; margin-right: 50px" >
	<div id="main">
		<c:forEach items="${listPlayer}" var ="player" varStatus="loop">
			<div class="circle"> 
				<!-- div intermediario para corregir la rotacion dentro -->
			    <div style="transform: rotate(calc(360deg - var(--angle)* ${ loop.index })">
			    	<div style="border-radius: 10px;">
				    <c:choose>
				    	<c:when test="${ game.currentPlayer.equals(player) }">
				    		<button class = "player" style="border-radius: 10px; background-color: green;" onclick="myFunction(${player.getUser().getUsername()})"> ${player.getUser().getUsername()} </button>
				    	<</c:when>
				    	<c:when test="${ player.getRol().toString().equals('SHOGUN') }">
				    		<button class = "player" style="border-radius: 10px; background-color: blue;" onclick="myFunction(${player.getUser().getUsername()})"> ${ player.getUser().getUsername() } </button>
				    	</c:when>
				    	<c:when test="${ player.getUser().getUsername().equals(POVplayer.getUsername()) }">
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
</div> 
<c:forEach items="${listPlayer}" var ="player" varStatus="loop">
	<div id="${player.getUser().getUsername()}" class="division">
		<table>
			<tr>
				<th>
					<p style ="color:#f2f2f2" >Vida: ${ player.getCurrentHearts() }</p>
				</th>
				<th>
					<p style ="color:#f2f2f2" >Honor: ${ player.getHonor() }</p>
				</th>
				<th>
					<p style ="color:#f2f2f2" >Cartas: ${ player.getHand().size() }</p>
				</th>
			</tr>
			<tr style="background-color:grey;">
				<td >
					<spring:url value="/resources/images/cards/armadura.png" htmlEscape="true" var="equipment1" /> 
					<c:choose>
						<c:when test="${ player.getDistanceBonus()==0 }">
							<p><img style="float: left; width: 180px; opacity: 0.5;" title="" src="${equipment1}" id="equipment1" /> x${ player.getDistanceBonus() }</p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 180px;" title="" src="${equipment1}" id="equipment1" /> x${ player.getDistanceBonus() }</p>
						</c:otherwise>
					</c:choose>
					
					
				</td>
				<td>
					<spring:url value="/resources/images/cards/desenvainado rapido.png" htmlEscape="true" var="equipment2" />
					<c:choose>
						<c:when test="${ player.getDistanceBonus()==0 }">
							<p><img style="float: left; width: 180px; opacity: 0.5;" title="" src="${equipment2}" id="equipment2" /> x${ player.getDamageBonus() } </p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 180px;" title="" src="${equipment2}" id="equipment2" /> x${ player.getDamageBonus() } </p>
						</c:otherwise>
					</c:choose> 
					
				</td>
				<td>
					<spring:url value="/resources/images/cards/concentracion.png" htmlEscape="true" var="equipment3" /> 
					<c:choose>
						<c:when test="${ player.getDistanceBonus()==0 }">
							<p><img style="float: left; width: 180px; opacity: 0.5;" title="" src="${equipment3}" id="equipment3" /> x${ player.getWeaponBonus() } </p>
						</c:when>
						<c:otherwise>
							<p><img style="float: left; width: 180px;" title="" src="${equipment3}" id="equipment3" /> x${ player.getWeaponBonus() } </p>
						</c:otherwise>
					</c:choose>
					
				</td>
				<td>
					<spring:url value="/resources/images/${ player.getCharacter().getImage() }" htmlEscape="true" var="character" /> 
					<img style="float: left; width: 180px;" title="" src="${character}" id="character" />
				</td>
				<td>
					<c:choose>
						<c:when test="${ player.getRol().toString().equals('SHOGUN') }">
							<spring:url value="/resources/images/roles/${ player.getRol() }.png" htmlEscape="true" var="Rol" /> 
							<img style="float: left; width: 180px;" title="" src="${Rol}" id="Rol" />
						</c:when>
						<c:otherwise>
							<spring:url value="/resources/images/roles/ninguno.png" htmlEscape="true" var="Rol" /> 
							<img style="float: left; width: 180px;" title="" src="${Rol}" id="Rol" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</div>
</c:forEach>
                                        <div  id="equipment">
							<c:forEach items="${ game.currentPlayer.equipment }" var ="card" varStatus="loop">
				    							<img style="height:120px; width:auto;" src="/resources/images/cards/${card.name}.png" alt="card"/>
				    								</c:forEach>
							</div>
							<div style="display: inline-block; width: 45%; height: 50%; text-align:center; vertical-align: top; margin-right:10px; ">
								<form:form action="/game/end-turn">
									<input type="hidden" name="gameId" value="${ game.id }"></input>
									<button id="btn-end-turn" class="button"> END TURN </button>
								</form:form>
							</div>

<!-- por aqui voy -->
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
