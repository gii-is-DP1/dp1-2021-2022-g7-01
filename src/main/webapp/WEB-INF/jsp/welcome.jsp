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
[id^="welcome"] {
	background: /resources/images/roles/ninja.png
  no-repeat bottom fixed; 
	text-align: center;
	padding: 20px;
	position: absolute;
	left: 500px;
	top: 50px;
}
  
  

</style>
<petclinic:layout pageName="home">
    <h2 align="center"><fmt:message key="welcome" /></h2>
    <div class="row" >
        <div class="col-md-12" align="center" id="welcome" style="width: 200px; height: 130px;">
            <sec:authorize access="!isAuthenticated()">
          
            	<form method="post" action="/login">
 					<button type="submit" class="btn btn-default" id = "Login">Login</button>
				</form>
				<form method="get" action="/users/new">
					<button type="submit" class="btn btn-default" id = "Register">Register</button>
				</form>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<form method="get" action="/logout" style="padding-top: 30px">
 					<button type="submit" class="btn btn-default" id = "Logout">Logout</button>
				</form>
				</sec:authorize>
        </div>
    </div>
</petclinic:layout>
