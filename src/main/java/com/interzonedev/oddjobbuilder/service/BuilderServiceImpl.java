package com.interzonedev.oddjobbuilder.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

@Named("builderService")
public class BuilderServiceImpl implements BuilderService {

	private final Logger log = (Logger) LoggerFactory.getLogger(getClass());

	private final String userDir = System.getProperty("user.dir");

	private final String tempDirPath = userDir + "/tmp";

	@PostConstruct
	public void init() {
		log.debug("init: userDir = " + userDir);
	}

	@Override
	public String buildLibrary(boolean includeAjax, boolean includeLogger, boolean includeJQueryUtils) throws Exception {

		log.debug("buildLibrary: Start");

		initDirectories();

		log.debug("buildLibrary: End");

		return null;

	}

	private void initDirectories() throws IOException {

		String tempDirCanonicalPath = makeDirectory(tempDirPath);

		log.debug("Using temp directory: " + tempDirCanonicalPath);

		String repoDirPath = tempDirCanonicalPath + "/repo";
		makeDirectory(repoDirPath);

		String buildDirPath = tempDirCanonicalPath + "/build";
		makeDirectory(buildDirPath);

	}

	private String makeDirectory(String dirPath) throws IOException {

		boolean created = false;

		File dir = new File(dirPath);

		if (!dir.exists()) {
			boolean madeTempDir = dir.mkdir();

			if (!madeTempDir) {
				throw new IOException("Unable to create the tmp directory: " + dirPath);
			}

			dir.deleteOnExit();

			created = true;
		}

		String dirCanonicalPath = dir.getCanonicalPath();

		if (created) {
			log.debug("Created directory: " + dirCanonicalPath);
		} else {
			log.debug("Directory exists: " + dirCanonicalPath);
		}

		return dirCanonicalPath;

	}
}
