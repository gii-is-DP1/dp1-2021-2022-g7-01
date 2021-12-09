<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<style>
body {

}


button {
 
}

.container-fluid{
  height: 100%; 
  background-image: url(https://entretenimientodigital.net/wp-content/uploads/2020/11/samurai-sword.jpg);
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  background-opacity: 50%;
  padding-top: 50px;
  justify-content: center
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
		
	    <div class="row">
	    	
	       		<div class="col-md-4" ></div>
				<div class="col-md-4" style="background-color: #c4aead; padding-top: 50px; border-radius: 10px; margin: 0 auto; height: 280px; border: solid black 1px">
					<h2 align="center" >WELCOME</h2>
					</br>
					<div  align="center">
						<sec:authorize access="!isAuthenticated()">
		          
		            	<form method="post" action="/login" >
		 					<button type="submit" class="btn" style="width: 100px; border-radius: 10px; background-color: #1a1110; color:white" id = "Login">Login</button>
						</form>
						<form method="get" action="/users/new">
							<button type="submit" class="btn" style="width: 100px; border-radius: 10px; background-color: #1a1110; color:white" id = "Register">Register</button>
						</form>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
							<form method="get" action="/game/new">
		 					<button type="submit" class="btn btn-default" id = "Logout">NEW GAME!</button>
						</form>
					</sec:authorize>
					</div>
		            
				</div>
				<div class="col-md-4"></div>
	        	
	       
		</div>
 
</petclinic:layout>

