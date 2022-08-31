<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="owners">
	<h2>Edit ${user.username}</h2>
	<form:form modelAttribute="user" class="form-horizontal"
		action="/users/profile/edit" id="edit-user-form">
		<div class="form-group has-feedback">
			<form:hidden path="username" />
			<petclinic:inputField label="Email" name="email" />
			<petclinic:inputField label="Password" name="password" />
		</div>
		<div class="form-group">
			<button class="btn btn-default" type="submit">Update user</button>
		</div>
		<div style="visibility:hidden; ">
			<petclinic:inputField label="listFriends" name="listFriends" ></petclinic:inputField>
			<petclinic:inputField label="listFriendsOf" name="listFriendsOf"></petclinic:inputField>
			<petclinic:inputField label="listPlayers" name="listPlayers" ></petclinic:inputField>
			<petclinic:inputField label="avatar" name="avatar" ></petclinic:inputField>
			<petclinic:inputField label="enabled" name="enabled" ></petclinic:inputField>
			<petclinic:inputField label="listRequests" name="listRequests" ></petclinic:inputField>
			<petclinic:inputField label="listRequestsOf" name="listRequestsOf" ></petclinic:inputField>
			<petclinic:inputField label="listRequests" name="listRequests" ></petclinic:inputField>
		</div>
		
	</form:form>
</petclinic:layout>
