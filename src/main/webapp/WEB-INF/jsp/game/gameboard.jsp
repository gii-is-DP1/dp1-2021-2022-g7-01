<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<style>
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
    height: 50px;
    width: 50px;
    border: 2px dotted #000000;
    border-radius: 50%;
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

.btn{
	width:100%;
	height: 30%;
	border-radius: 10px;
	justify-content: center;
	border: none;
    color: white;
     text-shadow: 2px 0 0 #000, -2px 0 0 #000, 0 2px 0 #000, 0 -2px 0 #000, 1px 1px #000, -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000;
}

.btn:nth-child(1n){
	background-color: blue;
	margin-bottom: 3%;
}
.btn:nth-child(2n){
	background-color: yellow;
	margin-bottom: 3%;
}

.btn:nth-child(3n){
	background-color: red;
	margin-bottom: 3%;
}


</style>

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
	
	<div style="margin: auto; padding-top: 30px" >
		    <div style="display: inline-block; width: 55%; height: 90%; text-align:center; vertical-align: top; margin-right: 50px " >
			    
			    <div id="main">
			    	<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
			    		<div class="circle"> 
			    			<!-- div intermediario para corregir la rotacion dentro -->
			    			<div style="transform: rotate(calc(360deg - var(--angle)* ${ loop.index })">${ player.getUser().getUsername()}</div>
			    		</div>
			    	</c:forEach>
			    </div>
			    
    		</div> 
    		<div style="display: inline-block; width: 28%; height: 50%">
    			<div style="display: inline-block; width: 45%; height: 50%; text-align:center; vertical-align: top; margin-right:10px; ">
    			
					<button class="btn"> EQUIP CARD </button>
					<button class="btn"> USE CARD </button>
					<button class="btn"> ATTACK PLAYER </button>
					
				</div>
				<div  style="display: inline-block; width: 45%; height: 50%; text-align:center; vertical-align: top" id="border">
					<c:forEach items="${ listPlayer }" var ="player" varStatus="loop">
			    		<c:if test="${ player.getUser().getUsername().equals(user.getUsername()) }">
			    			<div>
			    				<img src="/resources/images/${ player.getCharacter().getImage() }" alt="character"
			    				style="height: 80%; width:auto"
			    				></img>
			    			</div>
			    			<div>
			    				<div style="display: inline-block">
			    					<img src="/resources/images/honorLive/live.png" alt="live" style="width: 10%; height: auto; display: inline-block;"></img>
			    					<p style="display: inline-block;"> ${ player.getCurrentHearts() } </p>
			    				</div>
			    				<div style="display: inline-block">
			    					<img src="/resources/images/honorLive/honor.png" alt="live" style="width: 10%; height: auto"></img>	
			    					<p style="display: inline-block;"> ${ player.getHonor() } </p>	
			    				</div>
			    			</div>
			    		</c:if>
			    	</c:forEach>			
				</div>
				
				<div id="border" style=" height: 60%; padding-top: 10px; margin-top: 10px">
				Aqui tu mano
				
				</div>		
				<div id="border" style=" height: 60%; padding-top: 10px; margin-top: 10px">
				Aqui cartas equipadas
				</div>		
    		</div>
	</div>
</div>


















