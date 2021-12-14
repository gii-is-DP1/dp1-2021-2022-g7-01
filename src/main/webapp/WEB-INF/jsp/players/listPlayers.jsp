<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <h2>Owners</h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th>Username</th>
            <th>Game</th>
            <th>Max Hearts</th>
            <th>Current Hearts</th>
            <th>Honor</th>
            <th>Position</th>
            <th>wonGame</th>
            <th>Rol</th>
            <th>esInofensivo</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listPlayers}" var="player">
            <tr>
                <td>
                    <c:out value="${player.user.username}"/>
                </td>
                 <td>
                    <c:out value="${player.game.id}"/>
                </td>
                <td>
                    <c:out value="${player.maxHearts}"/>
                </td>
                <td>
                    <c:out value="${player.currentHearts}"/>
                </td>
                <td>
                    <c:out value="${player.honor}"/>
                </td>
                 <td>
                    <c:out value="${player.position}"/>
                </td>
                 <td>
                    <c:out value="${player.wonGame}"/>
                </td>
                 <td>
                    <c:out value="${player.rol}"/>
                </td>
                  <td>
                    <c:out value="${player.esInofensivo}"/>
                </td>
                            
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>