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
            <th>Send request</th>
            
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
                <td>
                <form:form class="form-horizontal"
							action="/friendRequest/SendRequest/${user.username}"
							id="edit-user-form">
							<button class="btn btn-default" type="submit">send friend request</button>
						</form:form>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
