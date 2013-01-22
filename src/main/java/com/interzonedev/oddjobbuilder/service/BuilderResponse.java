package com.interzonedev.oddjobbuilder.service;

public class BuilderResponse {

	private final int coreLibrarySize;

	private final int ajaxComponentSize;

	private final int loggerComponentSize;

	private final int jQueryUtilsComponentSize;

	private final int totalLibrarySize;

	private final String compressedLibraryContents;

	private final String downloadFilename;

	public BuilderResponse(int coreLibrarySize, int ajaxComponentSize, int loggerComponentSize,
			int jQueryUtilsComponentSize, int totalLibrarySize, String compressedLibraryContents,
			String downloadFilename) {
		this.coreLibrarySize = coreLibrarySize;
		this.ajaxComponentSize = ajaxComponentSize;
		this.loggerComponentSize = loggerComponentSize;
		this.jQueryUtilsComponentSize = jQueryUtilsComponentSize;
		this.totalLibrarySize = totalLibrarySize;
		this.compressedLibraryContents = compressedLibraryContents;
		this.downloadFilename = downloadFilename;
	}

	public int getCoreLibrarySize() {
		return coreLibrarySize;
	}

	public int getAjaxComponentSize() {
		return ajaxComponentSize;
	}

	public int getLoggerComponentSize() {
		return loggerComponentSize;
	}

	public int getJQueryUtilsComponentSize() {
		return jQueryUtilsComponentSize;
	}

	public int getTotalLibrarySize() {
		return totalLibrarySize;
	}

	public String getCompressedLibraryContents() {
		return compressedLibraryContents;
	}

	public String getDownloadFilename() {
		return downloadFilename;
	}

}
