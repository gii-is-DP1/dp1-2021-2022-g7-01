<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, users, comments, achievements or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'users'}" url="/users/find"
					title="find users">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Find users</span>
				</petclinic:menuItem>



				<petclinic:menuItem active="${name eq 'comments'}" url="/comments"
					title="comments">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Comments</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'achievements'}"
					url="/achievements" title="achievements">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Achievements</span>
				</petclinic:menuItem>


				<petclinic:menuItem active="${name eq 'cards'}" url="/cards"
					title="cards">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Cards</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'listGame'}" url="/game/list"
					title="gameList">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Games</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'error'}" url="/error"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>

			</ul>
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user">
						</span>� <strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"> </span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/users/myprofile" />"
													class="btn btn-primary btn-block btn-sm">Profile</a> <a
													href="<c:url value="/friendRequest" />"
													class="btn btn-primary btn-block btn-sm">Friend request</a>
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>


											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
