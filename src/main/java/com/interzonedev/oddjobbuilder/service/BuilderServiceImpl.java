package com.interzonedev.oddjobbuilder.service;

import java.io.File;

import javax.inject.Named;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

@Named("builderService")
public class BuilderServiceImpl implements BuilderService {

	private final Logger log = (Logger) LoggerFactory.getLogger(getClass());

	@Override
	public String buildLibrary(boolean includeAjax, boolean includeLogger, boolean includeJQueryUtils) throws Exception {

		String userDir = System.getProperty("user.dir");
		log.debug("buildLibrary: userDir = " + userDir);

		String tempDirPath = userDir + "/tmp";
		File tempDir = new File(tempDirPath);
		tempDir.mkdir();
		tempDir.deleteOnExit();

		String absolutePath = tempDir.getAbsolutePath();
		String canonicalPath = tempDir.getCanonicalPath();
		String path = tempDir.getPath();
		boolean isAbsolute = tempDir.isAbsolute();
		boolean isDirectory = tempDir.isDirectory();
		boolean isFile = tempDir.isFile();

		log.debug("buildLibrary: absolutePath = " + absolutePath);
		log.debug("buildLibrary: canonicalPath = " + canonicalPath);
		log.debug("buildLibrary: path = " + path);
		log.debug("buildLibrary: isAbsolute = " + isAbsolute);
		log.debug("buildLibrary: isDirectory = " + isDirectory);
		log.debug("buildLibrary: isFile = " + isFile);

		return null;
	}

}
