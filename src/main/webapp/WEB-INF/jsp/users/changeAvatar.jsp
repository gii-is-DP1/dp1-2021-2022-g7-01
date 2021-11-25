<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="avatar">
	<h2>Edit ${user.username}</h2>
	<form:form modelAttribute="user" class="form-horizontal"
		action="/users/profile/changeAvatar" id="edit-user-form">
		<div class="form-group has-feedback">
			<form:hidden path="username" />
			<form:hidden path="password" />
			<div class="control-group">
            <petclinic:selectField name="avatar" label="Avatar " names="${characters}" size="5"/>
                </div>
		</div>
		<div class="form-group">
			<button class="btn btn-default" type="submit">Update user</button>
		</div>
	</form:form>
</petclinic:layout>
