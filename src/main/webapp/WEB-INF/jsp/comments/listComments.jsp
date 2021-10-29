<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="comments">
	<h2>Comments</h2>
	<div class="container">
		<div style="width: 100%; display: flex; justify-content: flex-end">
			<a href="/comments/new" class="btn btn-default">Create comment</a>
		</div>
		<c:forEach items="${listComments}" var="comment">
			<article>
				<h3>${comment.title}</h3>
				<p>${comment.body}</p>
				<p style="text-align: right">${comment.createDate}</p>
				<p style="text-align: right">${comment.user.username}</p>
				<spring:url value="comments/edit/{id_comment}" var="editUrl">
					<spring:param name="id_comment" value="${comment.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit
					Comment</a>
				<spring:url value="comments/delete/{id_comment}" var="editUrl">
					<spring:param name="id_comment" value="${comment.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Delete
					Comment</a>
			</article>
			<hr style="border-top: 1px solid #34302D">
		</c:forEach>
	</div>
</petclinic:layout>