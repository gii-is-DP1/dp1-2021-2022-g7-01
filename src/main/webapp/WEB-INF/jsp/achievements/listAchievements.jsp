<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<style>
[id^="rol"] {
	width: 9%;
}

.division{
	display: none;
}

[id^="Bronce"] {
	background-color: #f6ecdb;
}

[id^="Plata"] {
	background-color: #dde4e6;
}

[id^="Oro"] {
	background-color: fbf2c9;
}

[id^="Diamante"] {
	background-color: e2f8fd;
}

table, th, td {
	padding: 5px;
}

table {
	width: 100%;
	border-spacing: 15px;
	border-collapse: collapse;
	table-layout: fixed;
}

th, td {
	border: 10px solid #f1f1f1;
	text-overflow: ellipsis;
	border-radius: 50px;
	overflow: hidden;
}
</style>


<petclinic:layout pageName="achievements">
	<h1 align="center">Achievements</h1>
	<div class="container" >
		<div style="width: 100%; display: flex; justify-content: flex-end">
			<a href="/achievements/manage" class="btn btn-default">Manage achievement</a>
		</div>
		
		<c:forEach items="${types2}" var="Rol">
		<button onclick="myFunction(${Rol})">${Rol}</button>
		</c:forEach>
		<c:forEach items="${types2}" var="Rol">
		
		<div id="${Rol}" class="division">
			<c:set value="0" var="vueltas" />
			<div align="center">
				<spring:url value="/resources/images/roles/${Rol}.png"
					htmlEscape="true" var="rol" />
				<h2>
					${Rol} <img class="img-responsive" src="${rol}" align="bottom"
						id="rol">
				</h2>

				<table>
					<tr>
						<c:forEach items="${mapPersonal}" var="achievement">
							<c:if test="${Rol == achievement.key.types}">
								<c:set value="${vueltas+1}" var="vueltas" />
								<c:if test="${achievement.value == 1}">
									<th id="${achievement.key.type}"><spring:url
										value="/resources/images/difficulty/${achievement.key.type}.png"
										htmlEscape="true" var="difficulty" /> <img
										style="float: left;" title="" src="${difficulty}"
										id="difficulty" />
										<h4 id="difum">${achievement.key.title}</h4>
										<p id="description">${achievement.key.body}
										<c:out value="${status.count}" />
										</p>
									</th>
									<c:if test="${vueltas>3}">
									</tr>
									<c:set value="0" var="vueltas" />
									<tr>
									</c:if>
								</c:if>
								<c:if test="${achievement.value == 0}">
									<th id="${achievement.key.type}" style ="opacity: 50%"><spring:url
										value="/resources/images/difficulty/${achievement.key.type}.png"
										htmlEscape="true" var="difficulty" /> <img
										style="float: left;" title="" src="${difficulty}"
										id="difficulty" />
										<h4 id="difum">${achievement.key.title}</h4>
										<p id="description">${achievement.key.body}
										<c:out value="${status.count}" />
										</p>
									</th>
									<c:if test="${vueltas>3}">
									</tr>
									<c:set value="0" var="vueltas" />
									<tr>
									</c:if>
								</c:if>
							</c:if>
						</c:forEach>

					</tr>
				</table>
				<hr style="border-top: 1px solid #34302D">
			</div>
			</div>
		</c:forEach>
		
	</div>
	<script>
	
	function myFunction(rol) {
		console.log(rol);
		//var x = document.getElementById(rol);
		if(rol.style.display == "inline"){
			rol.style.display = "none";
		}
		else{
			rol.style.display = "inline";
		}
	}
	</script>
</petclinic:layout>