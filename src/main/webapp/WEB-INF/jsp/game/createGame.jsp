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
	
	 <h2>Number of invitations </h2>
	 
        
    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">UserName</th>
            <th style="width: 150px;">Game</th>
            <th style="text-align: center;">Accept</th>
            <th style="text-align: center;">Decline</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listInvitations}" var="invitation">
            <tr>
                <td>
                    <spring:url value="game/{gameId}/invitation/{userAddresse}" var="userUrl">
                        <spring:param name="gameId" value="${gameId}"/>
                        <spring:param name="userAddresse" value="${userAddresse}"/>
                    </spring:url>
                    <a> ${invitation.userSender.username} </a>
                </td>
                <td>${invitation.game.id}</td>
                <td style="text-align: center;">
                <form:form class="form-horizontal"
							action="/game/${invitation.game.id}/invitation/accept/${invitation.id}"
							id="edit-user-form">
							<button class="btn btn-default" type="submit">Accept play</button>
				</form:form>
                </td>
      			<td style="text-align: center;">
                <form:form class="form-horizontal"
							action="/game/${invitation.game.id}/invitation/decline/${invitation.id}"
							id="edit-user-form">
							<button class="btn btn-default" type="submit">Decline play</button>
				</form:form>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>