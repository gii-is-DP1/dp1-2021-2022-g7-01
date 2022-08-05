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

#div1, #div2 {margin: 20px; float: right;}

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

<div style="text-align:center;" >
	
	<div style="margin: auto;" >
			<div style="display: inline-block; color: black; vertical-align:top; width: 10%; height: 60%;"> 
				
				
			</div>
		   
    		
    		
	    				
					<div align="right" style="display: inline-block; width: 45%; height: 50%; text-align:center">
					<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
			    		<c:if test="${ player.getUser().getUsername().equals(POVplayer.getUsername()) }">
			    		<div>
			    		
			    				
			    				<div id="div2" style="display: inline-block; border-radius: 20px; background-color: #DFDADA" class="img-wrap">
			    				<h2>Tu PERSONAJE</h2>
			    						<img src="/resources/images/${ player.getCharacter().getImage() }" alt="character" style="height: 20%; width:auto" />
			    						<p>${player.getCharacter().getName()}</p>
			    				</div>
			    				
			    				<div id="div1" style="display: inline-block; border-radius: 10px; background-color: #DFDADA">
			    			<h2>Tu ROL</h2>
			    			<img src="/resources/images/roles/${player.getRol()}.png" alt="charactere" style="height: 50%; width:auto" />
			    				<p> ${player.getRol()}</p>
			    				</div>
			    				</div>
			    				
			    			
			    			
			    		</c:if>
			    </c:forEach>			
				</div>
				
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
