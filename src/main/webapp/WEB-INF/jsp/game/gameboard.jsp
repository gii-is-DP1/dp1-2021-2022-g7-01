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
	margin-left: 5%;
    position: relative;
    border: 2px dotted #000000;
    border-radius: 50%;
    width: 900px;
    height: 900px;
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
	--angle : 51deg;
}

/*el primero no lo desplazamos*/
.circle:nth-child(1n) {
    transform: translateX(450px);
    position: absolute;
}
/*desplazamos el resto de jugadores; cada cual más lejos con respecto al primero*/
.circle:nth-child(2n) {
    transform: rotate(calc(var(--angle))) translateX(450px);
    position: absolute;
}
.circle:nth-child(3n) {
    transform: rotate(calc(var(--angle)*2)) translateX(450px);
}
.circle:nth-child(4n) {
    transform: rotate(calc(var(--angle)*3)) translateX(450px);
}
.circle:nth-child(5n) {
    transform: rotate(calc(var(--angle)*4)) translateX(450px);
}
.circle:nth-child(6) {
    transform: rotate(calc(var(--angle)*5)) translateX(450px);
}
.circle:nth-child(7) {
    transform: rotate(calc(var(--angle)*6)) translateX(450px);
    position: absolute;
}

.innerCircle {
	-ms-transform: translateY(-50%); 
	transform: translateY(-50%);
}
</style>

<!-- EN CASO DE QUE NO SEAN 4 JUGADORES REAJUSTAMOS EL ANGULO DE SEPARACION QUE SERA DADO POR 360/Nºjugadores -->

<c:if test="${listPlayer.size().equals(5)}">
	<script type="text/javascript">
		var rootstyle = document.querySelector(":root").style;
		rootstyle.setProperty('--angle', '72deg');
	</script>
</c:if>
<c:if test="${listPlayer.size().equals(6)}">
	<script type="text/javascript">
		var rootstyle = document.querySelector(":root").style;
		rootstyle.setProperty('--angle', '60deg');
	</script>
</c:if>
<c:if test="${listPlayer.size().equals(7)}">
	<script type="text/javascript">
		var rootstyle = document.querySelector(":root").style;
		rootstyle.setProperty('--angle', '51deg');
	</script>
</c:if>



	<p>${listPlayer}</p>


<div style="text-align:center;" >
	<div style="margin: auto; padding-top: 30px" >
		    <div style="display: inline-block; width: 65%; height: auto; text-align:center; vertical-align: top; margin-right: 50px " id="border">
			    
			    <div id="main">
			    	<c:forEach begin="0" end="6" varStatus="loop">
			    		<div class="circle"> 
			    			<!-- div intermediario para corregir la rotacion dentro -->
			    			<div style="transform: rotate(calc(360deg - var(--angle)* ${ loop.index })">${ loop.index }</div>
			    		</div>
			    	</c:forEach>
			    	
			    </div>
			    
    		</div> 
    		<div style="display: inline-block; width: 28%">
    			<div style="display: inline-block; width: 45%; height: 500px; text-align:center; vertical-align: top; margin-right:10px" id="border">
				Aqui los botones
				</div>
				<div  style="display: inline-block; width: 45%; height: 500px; text-align:center; vertical-align: top" id="border">
				Aqui cartas equipadas
				</div>
				<div id="border" style=" height: 400px; padding-top: 10px; margin-top: 10px">
				Aqui tu mano
				</div>		
    		</div>
	</div>
</div>


















