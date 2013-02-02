<!DOCTYPE html>

<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

		<title>OddJob - Object Oriented JavaScript Framework - ${param.title}</title>

		<c:if test="${not empty param.description}">
			<meta name="description" content="${param.description}" />
		</c:if>

		<meta name="viewport" content="width=device-width" />

		<link rel="icon" href="<c:url value="/assets/img/favicon.png" />" type="image/png" />

		<link rel="stylesheet" href="<c:url value="/assets/css/normalize.css" />" />
		<link rel="stylesheet" href="<c:url value="/assets/css/main.css" />" />

		<c:if test="${not empty param.cssIncludes}">
			<jsp:include page="${param.cssIncludes}" />
		</c:if>

		<script src="<c:url value="/assets/js/lib/modernizr-2.6.2.min.js" />"></script>

		<c:if test="${not empty param.headJsIncludes}">
			<jsp:include page="${param.headJsIncludes}" />
		</c:if>
	</head>

	<body>
		<header class="topHeader clearfix">
			<h1>OddJob</h1>
			<nav>
				<ul>
					<li><a href="<c:url value="/home" />" <c:if test="${param.pageId eq 'home'}">class="current"</c:if>>Home</a></li>
					<li><a href="<c:url value="/docs" />" <c:if test="${param.pageId eq 'docs'}">class="current"</c:if>>Documentation</a></li>
					<li><a href="<c:url value="/builder" />" <c:if test="${param.pageId eq 'builder'}">class="current"</c:if>>Download</a></li>
				</ul>
			</nav>
		</header>

		<div class="mainContainer">
			<jsp:include page="${param.bodyContent}" />
		</div>

		<script src="<c:url value="/assets/js/lib/jquery-1.9.0.min.js" />"></script>

		<c:if test="${not empty param.bodyJsIncludes}">
			<jsp:include page="${param.bodyJsIncludes}" />
		</c:if>

		<c:if test="${not empty param.bodyJsSetUp}">
			<jsp:include page="${param.bodyJsSetUp}" />
		</c:if>
	</body>
</html>
