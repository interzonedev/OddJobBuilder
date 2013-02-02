<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url var="formAction" value="/builder" scope="page" />

<h2>Create custom builds of the OddJob JavaScript framework</h2>

<form:form modelAttribute="builderForm" action="${formAction}" method="post">

	<form:errors>
		<div class="formGroup formError">
			<form:errors cssClass="formError" />
		</div>
	</form:errors>

	<div class="formGroup <form:errors path="includeAjax">formError</form:errors>">
		<form:checkbox path="includeAjax" id="includeAjax" />
		<label for="includeAjax">Include the Ajax utils</label>
		<form:errors path="includeAjax"><p><form:errors path="includeAjax" cssClass="formError" /></p></form:errors>
	</div>

	<div class="formGroup <form:errors path="includeLogger">formError</form:errors>">
		<form:checkbox path="includeLogger" id="includeLogger" />
		<label for="includeLogger">Include the Logger wrapper class</label>
		<form:errors path="includeLogger"><p><form:errors path="includeLogger" cssClass="formError" /></p></form:errors>
	</div>

	<div class="formGroup <form:errors path="includeJQueryUtils">formError</form:errors>">
		<form:checkbox path="includeJQueryUtils" id="includeJQueryUtils" />
		<label for="includeJQueryUtils">Include the jQuery utils</label>
		<form:errors path="includeJQueryUtils"><p><form:errors path="includeJQueryUtils" cssClass="formError" /></p></form:errors>
	</div>

	<c:if test="${not empty builderResponse}">
		<div class="sizesContainer">
			<p>Core library size = ${builderResponse.coreLibrarySize} bytes</p>

			<c:if test="${builderResponse.ajaxComponentSize gt -1}">
				<p>Ajax component size = ${builderResponse.ajaxComponentSize} bytes</p>
			</c:if>

			<c:if test="${builderResponse.loggerComponentSize gt -1}">
				<p>Logger component size = ${builderResponse.loggerComponentSize} bytes</p>
			</c:if>

			<c:if test="${builderResponse.JQueryUtilsComponentSize gt -1}">
				<p>jQuery utils component size = ${builderResponse.JQueryUtilsComponentSize} bytes</p>
			</c:if>

			<p>Total library size = ${builderResponse.totalLibrarySize} bytes</p>
		</div>
	</c:if>

	<input id="getStatsTrigger" type="submit" name="stats" value="File Sizes" class="btn" />
	<input type="submit" name="download" value="Download" class="btn" />

</form:form>
