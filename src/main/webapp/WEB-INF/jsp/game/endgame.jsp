<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<petclinic:layout pageName="game">

<style>
img.estrella {
	position: absolute;
	bottom: 100px;
}
</style>

	<div class="row">
		<div class="col-sm-10">
			<div align="center">
				<h2>
					EQUIPO GANADOR ${ winnerRol }
				</h2>
			</div>
		</div>
	</div>
</petclinic:layout>