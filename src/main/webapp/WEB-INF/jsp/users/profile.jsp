<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<style>
	table {
		width: 25%;
		float: right;
	}
</style>

<petclinic:layout pageName="Profile">
	<c:set var="ownProfile"
		value="form-group ${status.error ? 'has-error' : '' }" />
	<div class="container">
		<h2>Profile</h2>
		<div class="row">
			<div class="col-sm-4" style="display: flex; align-items: center; flex-direction: column">
				
			<c:choose>
			    <c:when test="${userProfile.avatar == null}">
			        <img src="/resources/images/profile/default-image.jpg"
					style="width: 150px;" />
			        <br />
			    </c:when>    
			    <c:otherwise>
			        <img src="/resources/images/profile/${userProfile.avatar}"
					style="width: 150px;" />
			        <br />
			    </c:otherwise>
			</c:choose>
			<a class="btn btn-default"
			href="<c:url value="/users/profile/changeAvatar/${userProfile.username}" />">Change avatar</a>
			</div>
			<div class="col-sm-8">
				<div class="row" style="margin: 0 0 20px 0">
					<div class="col-sm-6">
						<h3>Username: ${userProfile.username}</h3>
					</div>
					<div class="col-sm-6">
						<h3>Email: ${userProfile.email}</h3>
					</div>
				</div>
				<div style="display: flex; justify-content: space-around;">
					<c:if test="${userProfile.username == username}">
						<a class="btn btn-default"
							href="<c:url value="/users/profile/edit/${userProfile.username}" />">Edit
							profile</a>
					</c:if>
					<c:if test="${userProfile.username != username}">
						<form:form class="form-horizontal"
							action="/friendRequest/SendRequest/${userProfile.username}"
							id="edit-user-form">
							<button class="btn btn-default" type="submit">Send friend request</button>
						</form:form>
					</c:if>
					<a class="btn btn-default"
						href="<c:url value="/users/statistics/${userProfile.username}" />">Statistics</a>
					<a class="btn btn-default"
						href="<c:url value="/users/game-history/${userProfile.username}" />">Game
						history</a>
				</div>
			</div>
		</div>
		<table id="friendsTable" class="table table-striped", style = "width: 25%; float: right;">
			<thead>
	        <tr>
	            <th>Friends</th>
	            
	        </tr>
	        </thead>
        	<tbody>
        	<c:forEach items="${friends}" var="friend">
        		<tr>
        		<td>
        			<spring:url value="/users/profile/{username}" var="userUrl">
                        <spring:param name="username" value="${friend}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(userUrl)}"><c:out value="${friend}"/>
        		</td>
        		</tr>
        	</c:forEach>
        	</tbody>
        	
		</table>
	</div>
</petclinic:layout>
