<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Profile">
	<c:set var="ownProfile"
		value="form-group ${status.error ? 'has-error' : '' }" />
	<div class="container">
		<h2>Profile</h2>
		<div class="row">
			<div class="col-sm-4" style="display: flex; justify-content: center">
				<img src="/resources/images/profile/default-image.jpg"
					style="width: 150px;" />
			</div>
			<div class="col-sm-8">
				<form:form modelAttribute="userProfile" class="form-horizontal"
					id="user-form" style="margin: 10px 0">
					<petclinic:inputField label="Username" name="username"
						readonly="true" />
					<petclinic:inputField label="Email" name="email" readonly="true" />
				</form:form>
				<div style="display: flex; justify-content: space-around;">
					<c:if test="${userProfile.username == username}">
						<button class="btn btn-default" type="submit">Edit
							profile</button>
					</c:if>
					<a class="btn btn-default"
						href="<c:url value="/stats/${userProfile.username}" />">Statistics</a>
					<a class="btn btn-default"
						href="<c:url value="/history/${userProfile.username}" />">Game
						history</a>
				</div>
			</div>
		</div>
	</div>
</petclinic:layout>
