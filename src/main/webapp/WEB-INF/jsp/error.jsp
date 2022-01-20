<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<style>
img{
width:350px;

height:350px;

position: fixed;

top: 25%;

left: 37%;
}

</style>
<petclinic:layout pageName="error">
	
	 <h2 align="center">It seems that you are mistaken...</h2>
    <spring:url value="/resources/images/samuraiDog.gif" var="samuraiDog"/>
    <img style="float: ;" src="${samuraiDog}"/>

   

    <p>${exception}</p>
    <p>${errorCode}</p>

</petclinic:layout>
