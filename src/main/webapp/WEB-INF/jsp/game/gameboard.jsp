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
				
	</div>
</div>


















