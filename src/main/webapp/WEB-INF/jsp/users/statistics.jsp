<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Profile">
	<div class="container">
		<h2>Statistics</h2>
		<div class="row">
			<div class="col-sm-6">
				<h3>Played games: ${playedGames}</h3>
			</div>
			<div class="col-sm-6">
				<h3>Won games: ${playedGames}</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<h3>Lost games: ${lostGames}</h3>
			</div>
			<div class="col-sm-6">
				<h3>Shogun Games: ${shogunGames}</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<h3>Samurai games: ${samuraiGames}</h3>
			</div>
			<div class="col-sm-6">
				<h3>Ninja games: ${ninjaGames}</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<h3>Ronin games: ${roninGames}</h3>
			</div>
		</div>
	</div>
</petclinic:layout>
