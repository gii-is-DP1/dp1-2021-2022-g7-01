<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="achievements/manage">
    <h2>Manage achievements</h2>
		
    <table id="cardsTable" class="table table-striped">
        <thead>
        <tr>
       		<th style="width: 150px;">Title</th>
            <th style="width: 200px;">Body</th>
            <th style="width: 120px">Dificulty</th>
            <th style="width: 120px">Role</th>
            <th style="width: 80px">Admin</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listAchievements}" var="achievement">
            <tr>
                
                <td>
                    <c:out value="${achievement.title}"/>
                </td>
                <td>
                <c:out value="${achievement.body}"/>
                </td>
                <td>
                    <img src="/resources/images/difficulty/${achievement.type}.png"
					style="width: 150px;" />
                </td>
                <td>
                <img src="/resources/images/roles/${achievement.types}.png"
					style="width: 150px;" />
                </td>
                 <td>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Delete
						Achievement</a>
                </td>
                
            </tr>
        </c:forEach>
		</tbody>	
    	</table>
</petclinic:layout>
