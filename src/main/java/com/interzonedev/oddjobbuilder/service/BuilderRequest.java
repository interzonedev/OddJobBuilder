package com.interzonedev.oddjobbuilder.service;

public class BuilderRequest {

    private final boolean includeAjax;

    private final boolean includeLogger;

    private final boolean includeJQueryUtils;

    public BuilderRequest(boolean includeAjax, boolean includeLogger, boolean includeJQueryUtils) {
        this.includeAjax = includeAjax;
        this.includeLogger = includeLogger;
        this.includeJQueryUtils = includeJQueryUtils;
    }

    public boolean isIncludeAjax() {
        return includeAjax;
    }

    public boolean isIncludeLogger() {
        return includeLogger;
    }

    public boolean isIncludeJQueryUtils() {
        return includeJQueryUtils;
    }

}
