<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>Requested friends</h2>
        <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">UserName</th>
            <th style="width: 200px;">Accept</th>
            <th style="width: 200px;">Decline</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${friendRequest}" var="friendRequest">
            <tr>
                <td>
                    <spring:url value="/users/profile/{username}" var="userUrl">
                        <spring:param name="username" value="${friendRequest}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(userUrl)}"> ${friendRequest} </a>
                </td>
                <td>
                <form:form class="form-horizontal"
							action="/friendRequest/AcceptRequest/${friendRequest}"
							id="edit-user-form">
							<button class="btn btn-default" type="submit">Accept friend request</button>
				</form:form>
                </td>
      			<td>
                <form:form class="form-horizontal"
							action="/friendRequest/declineRequest/${friendRequest}"
							id="edit-user-form">
							<button class="btn btn-default" type="submit">Decline friend request</button>
				</form:form>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
