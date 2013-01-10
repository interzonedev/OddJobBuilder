<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url var="formAction" value="/builder" scope="page" />

<form:form modelAttribute="builderForm" action="${formAction}" method="post">

	<form:errors>
		<div class="control-group error">
			<form:errors cssClass="help-inline" />
		</div>
	</form:errors>

	<div class="control-group <form:errors path="includeAjax">error</form:errors>">
		<form:checkbox path="includeAjax" id="includeAjax" />
		<label for="includeAjax">Include the Ajax utils</label>
		<form:errors path="includeAjax"><p><form:errors path="includeAjax" cssClass="help-inline" /></p></form:errors>
	</div>

	<div class="control-group <form:errors path="includeLogger">error</form:errors>">
		<form:checkbox path="includeLogger" id="includeLogger" />
		<label for="includeLogger">Include the Logger wrapper class</label>
		<form:errors path="includeLogger"><p><form:errors path="includeLogger" cssClass="help-inline" /></p></form:errors>
	</div>

	<div class="control-group <form:errors path="includeJQueryUtils">error</form:errors>">
		<form:checkbox path="includeJQueryUtils" id="includeJQueryUtils" />
		<label for="includeJQueryUtils">Include the jQuery utils</label>
		<form:errors path="includeJQueryUtils"><p><form:errors path="includeJQueryUtils" cssClass="help-inline" /></p></form:errors>
	</div>

	<input type="submit" value="Submit" class="btn" />

</form:form>
