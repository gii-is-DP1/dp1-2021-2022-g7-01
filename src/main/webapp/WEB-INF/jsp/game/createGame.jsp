<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="game">
	<h2>
		<div align="center">
			<c:if test="${game['new']}">Create </c:if>
			Game

		</div>
	</h2>
	<br>
	<br>
	<br>


	<div align="center" class="form-group">




		<form:form>
		<button style="background-color: #FF9900" class="btn btn-default"
			type="submit">Create game</button>
		</form:form>



	</div>

</petclinic:layout>