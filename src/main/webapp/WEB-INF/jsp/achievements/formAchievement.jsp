<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="achievements">
    <h2>
        <c:if test="${achievement['new']}">New </c:if> Achievement
    </h2>
    <form:form modelAttribute="achievement" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="title"/>
            <petclinic:inputField label="Description" name="body"/>
            <div class="control-group">
            <petclinic:selectField name="type" label="Difficulty " names="${types1}" size="5"/>
                </div>
                <div class="control-group">
            <petclinic:selectField name="types" label="Rol " names="${types2}" size="5"/>
                </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${achievement['new']}">
                        <button class="btn btn-default" type="submit">Add Achievement</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Achievement</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>