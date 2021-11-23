<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  
<style>
body { 
  background: url(https://entretenimientodigital.net/wp-content/uploads/2020/11/samurai-sword.jpg)
  no-repeat bottom fixed; 
  background-size: 75%;
  background-opacity: 50%;
}

button {
  
  align-content:flex-end;
  
}

</style>
<petclinic:layout pageName="home">
    <h2 align="center"><fmt:message key="welcome"/></h2>
    <div class="row">
        <div class="col-md-12" align="center">
            <sec:authorize access="!isAuthenticated()">
          
            	<form method="post" action="/login">
 					<button type="submit" class="btn btn-default" id = "Login">Login</button>
				</form>
				<form method="get" action="/users/new">
					<button type="submit" class="btn btn-default" id = "Register">Register</button>
				</form>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<form method="get" action="/logout">
 					<button type="submit" class="btn btn-default" id = "Logout">Logout</button>
				</form>
				</sec:authorize>
        </div>
    </div>
</petclinic:layout>
