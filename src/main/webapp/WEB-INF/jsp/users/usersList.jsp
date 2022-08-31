<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>Users</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">UserName</th>
            <th style="width: 200px;">Email</th>
            <th style="text-align: center;">Send request</th>
            <th style="text-align: center;">Admin</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="user">
            <tr>
                <td>
                    <spring:url value="/users/profile/{username}" var="userUrl">
                        <spring:param name="username" value="${user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(userUrl)}"><c:out value="${user.username}"/></a>
                </td>
                <td>
                    <c:out value="${user.email}"/>
                </td>
                <td style="text-align: center;">
                <form:form class="form-horizontal"
							action="/friendRequest/SendRequest/${user.username}"
							id="edit-user-form">
							<c:if test="${!listFriend.contains(user.username) && !user.username.equals(username)}">
							<button class="btn btn-default" type="submit">send friend request</button></c:if>
						</form:form>
                </td>
                <td>
                	<div style="width: 100%; display: flex; justify-content: flex-end">
                	<c:if test="${authority==true}">
						<a href="/users/delete/${user.username}" class="btn btn-default">Delete User</a>
					</c:if>
					</div>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <c:forEach items="${pages}" var="page">
    	<a href="/users/${page}"><c:out value="${page}"></c:out></a>
    </c:forEach>
</petclinic:layout>
