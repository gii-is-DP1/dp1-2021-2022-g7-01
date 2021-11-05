<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="logros">
	<h1 align = "center">Logros</h1>
	<div class="container">
		<div style="width: 100%; display: flex; justify-content: flex-end">
			<a href="/logros/new" class="btn btn-default">Create Logro</a>
		</div>
		<c:forEach items="${listLogros}" var="logro">
			<article>
			<div align = "center">	
				<h2 >ninja</h2>
				
				<spring:url value="/resources/images/roles/general.jpg" htmlEscape="true" var="petsImage"/>
            <img  class="img-responsive" src="${petsImage}" align = "bottom" />
            <table>
            	<tr>
            		<th>
            		<p><a href = "">Imagen logro 1</a>  descripción</p>
            		
            		</th>
            		<th>
            		<p><a href = "">Imagen logro 1</a>  descripción</p>
            		
            		</th>
            		<th>
            		<p><a href = "">Imagen logro 1</a>  descripción</p>
            		
            		</th>
            		<th>
            		<p><a href = "">Imagen logro 1</a>  descripción</p>
            		
            		</th>
            	</tr>
            </table>
			</div>
				<h3>${logro.title}</h3>
				<p>${logro.body}</p>
				<p>Dificultad: ${logro.type}</p>
				<p>Rol: ${logro.types}</p>
				 <p style="text-align: right">Logro creado por: ${logro.user.username}</p>
				<spring:url value="logros/edit/{id_logro}" var="editUrl">
					<spring:param name="id_logro" value="${logro.id}" />
				</spring:url>
				
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit
						Logro</a>
					<spring:url value="logros/delete/{id_logro}" var="editUrl">
						<spring:param name="id_logro" value="${logro.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Delete
						Logro</a>
				

			</article>

			<hr style="border-top: 1px solid #34302D">
		</c:forEach>
	</div>
</petclinic:layout>