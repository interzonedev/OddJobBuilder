package com.interzonedev.oddjobbuilder.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

@Named("builderService")
public class BuilderServiceImpl implements BuilderService {

	private final Logger log = (Logger) LoggerFactory.getLogger(getClass());

	private final String userDir = System.getProperty("user.dir");

	private final String tempDirPath = userDir + "/tmp";

	private static final String REPO_URL = "git://github.com/interzonedev/OddJob.git";

	private static final String REPO_TAG = "v1.2.0";

	private class WorkDirectories {

		private final String repoDirectoryPath;

		private final String buildDirectoryPath;

		public WorkDirectories(String repoDirectoryPath, String buildDirectoryPath) {
			this.repoDirectoryPath = repoDirectoryPath;
			this.buildDirectoryPath = buildDirectoryPath;
		}

		public String getRepoDirectoryPath() {
			return repoDirectoryPath;
		}

		public String getBuildDirectoryPath() {
			return buildDirectoryPath;
		}

	}

	@PostConstruct
	public void init() {
		log.debug("init: userDir = " + userDir);
	}

	@Override
	public String buildLibrary(boolean includeAjax, boolean includeLogger, boolean includeJQueryUtils) throws Exception {

		log.debug("buildLibrary: Start");

		WorkDirectories workDirectories = initDirectories();

		cloneRepo(workDirectories.getRepoDirectoryPath());

		log.debug("buildLibrary: End");

		return null;

	}

	private void cloneRepo(String repoDirectoryPath) throws InvalidRemoteException, TransportException, GitAPIException {

		File repoDirectory = new File(repoDirectoryPath);

		if (repoDirectory.listFiles().length != 0) {
			log.debug("cloneRepo: " + repoDirectory + " is not empty");
			return;
		}

		log.debug("cloneRepo: Cloning repo from " + REPO_URL);

		CloneCommand cloneCommand = new CloneCommand();

		Git git = cloneCommand.setURI(REPO_URL).setDirectory(repoDirectory).setBranch("master").call();

		git.checkout().setName(REPO_TAG).call();

		log.debug("cloneRepo: Cloned repo into " + repoDirectory + " and checked out " + REPO_TAG);

	}

	private WorkDirectories initDirectories() throws IOException {

		String tempDirCanonicalPath = makeDirectory(tempDirPath);

		log.debug("Using temp directory: " + tempDirCanonicalPath);

		String repoDirPath = tempDirCanonicalPath + "/repo";
		String repoDirCanonicalPath = makeDirectory(repoDirPath);

		String buildDirPath = tempDirCanonicalPath + "/build";
		String buildDirCanonicalPath = makeDirectory(buildDirPath);

		return new WorkDirectories(repoDirCanonicalPath, buildDirCanonicalPath);

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
