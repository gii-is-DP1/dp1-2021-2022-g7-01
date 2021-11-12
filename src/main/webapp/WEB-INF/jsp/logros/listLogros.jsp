<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<style>
[id^="difum"] {
  opacity: 50%;
}

[id^="rol"] {
  width: 9%;
}
[id^="Bronce"]{
  background-color: #f6ecdb;
}
[id^="Plata"]{
  background-color: #dde4e6;
}
[id^="Oro"]{
  background-color: fbf2c9;
}
[id^="Diamante"]{
  background-color: e2f8fd;
}

table{
  border: 1px solid black;
  border-collapse: collapse;
  text-align: center;
  width:100%;
  position: relative;
}

 th, td {
  border: 1px solid black;
  border-collapse: collapse;
  text-align: center;
}
th { 
 padding: 10px 50px;
 text-align: center;
 border: 1px solid #999;
            }
td { 
 padding: 5px 40px;
 text-align: center;
 border: 1px solid #999;
            }

 tr:nth-child(1) {
 background: #dedede;
 width:auto;
            }

</style>


<petclinic:layout pageName="logros">
	<h1 align = "center">Logros</h1>
	<div class="container">
		<div style="width: 100%; display: flex; justify-content: flex-end">
			<a href="/logros/new" class="btn btn-default">Create Logro</a>
		</div>
		<c:forEach items="${listLogros}" var="Rol">
		<div align = "center">
			<spring:url value="/resources/images/roles/${Rol.types}.jpg" htmlEscape="true" var="rol"/>
			<h2> ${Rol.types} <img class="img-responsive" src="${rol}" align = "bottom" id = "rol"> </h2> 
			
           <table>
           <tr>
			<c:forEach items="${listLogros}" var="logro">
				<c:if test = "${Rol.types == logro.types}">
				
					
					<th>
					<spring:url value="/resources/images/difficulty/${logro.type}.png" htmlEscape="true" var="difficulty"/>
            	<div id = "${logro.type}">
					<p id = "difum"> <img title="${logro.body}" src="${difficulty}"  id = "difficulty"/> ${logro.title}</p>
					</div>
					</th>
				</c:if>
				 
			</c:forEach>
				</tr>
				</table>

			<hr style="border-top: 1px solid #34302D">
		</div>
		</c:forEach>
	</div>
	
	
</petclinic:layout>