package com.interzonedev.oddjobbuilder.web.builder;

public class BuilderForm {

	private boolean includeAjax;

	private boolean includeLogger;

	private boolean includeJQueryUtils;

	public boolean isIncludeAjax() {
		return includeAjax;
	}

	public void setIncludeAjax(boolean includeAjax) {
		this.includeAjax = includeAjax;
	}

	public boolean isIncludeLogger() {
		return includeLogger;
	}

	public void setIncludeLogger(boolean includeLogger) {
		this.includeLogger = includeLogger;
	}

	public boolean isIncludeJQueryUtils() {
		return includeJQueryUtils;
	}

	public void setIncludeJQueryUtils(boolean includeJQueryUtils) {
		this.includeJQueryUtils = includeJQueryUtils;
	}

}
