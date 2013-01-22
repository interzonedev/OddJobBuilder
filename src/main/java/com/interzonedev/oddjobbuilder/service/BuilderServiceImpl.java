package com.interzonedev.oddjobbuilder.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

@Named("builderService")
public class BuilderServiceImpl implements BuilderService {

	private final Logger log = (Logger) LoggerFactory.getLogger(getClass());

	private final String userDir = System.getProperty("user.dir");

	private String workDirPath;

	private String relativeRepoDirPath;

	private String relativeBuildDirPath;

	private String repoUrl;

	private String repoTag;

	private String javaScriptSourceDir;

	private List<String> requiredFilenames;

	private Map<String, String> optionalFilenames;

	private String downloadFilename;

	@Inject
	@Named("builderProperties")
	private Properties builderProperties;

	@Inject
	@Named("errorReporter")
	private ErrorReporter errorReporter;

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
		String relativeWorkDirPath = builderProperties.getProperty("builder.work.directory");
		workDirPath = userDir + relativeWorkDirPath;

		relativeRepoDirPath = builderProperties.getProperty("builder.repo.directory");
		relativeBuildDirPath = builderProperties.getProperty("builder.build.directory");

		repoUrl = builderProperties.getProperty("repo.url");
		repoTag = builderProperties.getProperty("repo.tag");

		javaScriptSourceDir = builderProperties.getProperty("compress.javascript.source.dir");

		String requiredFilenamesValues = builderProperties.getProperty("compress.requiredFilenames");
		requiredFilenames = Arrays.asList(requiredFilenamesValues.split("\\s*,\\s*"));

		String optionalComponentsValues = builderProperties.getProperty("compress.optionalComponents");
		List<String> optionalComponents = Arrays.asList(optionalComponentsValues.split("\\s*,\\s*"));

		optionalFilenames = new HashMap<String, String>();
		for (String optionalComponent : optionalComponents) {
			optionalFilenames.put(optionalComponent,
					builderProperties.getProperty("compress.optionalFilename." + optionalComponent));
		}

		downloadFilename = builderProperties.getProperty("compress.downloadFilename");

		log.debug("init: workDirPath = " + workDirPath);
	}

	@Override
	public BuilderResponse buildLibrary(BuilderRequest builderRequest) throws Exception {

		log.debug("buildLibrary: Start");

		WorkDirectories workDirectories = initDirectories();

		cloneRepo(workDirectories.getRepoDirectoryPath());

		BuilderResponse builderResponse = compressJavaScript(workDirectories.getRepoDirectoryPath(),
				workDirectories.getBuildDirectoryPath(), downloadFilename, builderRequest);

		log.debug("buildLibrary: End");

		return builderResponse;

	}

	private BuilderResponse compressJavaScript(String repoDirectoryPath, String buildDirectoryPath,
			String downloadFilename, BuilderRequest builderRequest) throws EvaluatorException, IOException {

		StringBuilder compressedContent = new StringBuilder();

		for (String requiredFilename : requiredFilenames) {
			String compressedRequiredFileContent = compressJavaScriptFile(repoDirectoryPath, requiredFilename);
			compressedContent.append(compressedRequiredFileContent);
		}
		int coreLibrarySize = compressedContent.length();
		log.info("compressJavaScript: Core library compressed size = " + coreLibrarySize);

		int ajaxComponentSize = -1;
		int loggerComponentSize = -1;
		int jQueryUtilsComponentSize = -1;

		if (builderRequest.isIncludeAjax()) {
			String ajaxFilename = optionalFilenames.get("ajax");
			String compressedAjaxFileContent = compressJavaScriptFile(repoDirectoryPath, ajaxFilename);
			compressedContent.append(compressedAjaxFileContent);
			ajaxComponentSize = compressedAjaxFileContent.length();
			log.info("compressJavaScript: Ajax component compressed size = " + ajaxComponentSize);
		}

		if (builderRequest.isIncludeLogger()) {
			String loggerFilename = optionalFilenames.get("logger");
			String compressedLoggerFileContent = compressJavaScriptFile(repoDirectoryPath, loggerFilename);
			compressedContent.append(compressedLoggerFileContent);
			loggerComponentSize = compressedLoggerFileContent.length();
			log.info("compressJavaScript: Logger component compressed size = " + loggerComponentSize);
		}

		if (builderRequest.isIncludeJQueryUtils()) {
			String jQueryUtilsFilename = optionalFilenames.get("jQueryUtils");
			String compressedJQueryUtilsFileContent = compressJavaScriptFile(repoDirectoryPath, jQueryUtilsFilename);
			compressedContent.append(compressedJQueryUtilsFileContent);
			jQueryUtilsComponentSize = compressedJQueryUtilsFileContent.length();
			log.info("compressJavaScript: jQuery utils component compressed size = " + jQueryUtilsComponentSize);
		}

		int totalLibrarySize = compressedContent.length();
		log.info("compressJavaScript: Total library compressed size = " + totalLibrarySize);

		String compressedLibraryContents = compressedContent.toString();

		BuilderResponse builderResponse = new BuilderResponse(coreLibrarySize, ajaxComponentSize, loggerComponentSize,
				jQueryUtilsComponentSize, totalLibrarySize, compressedLibraryContents, downloadFilename);

		return builderResponse;

	}

	private String compressJavaScriptFile(String repoDirectoryPath, String sourceJavaScriptFilename)
			throws EvaluatorException, IOException {

		String compressedContent = null;

		Reader sourceJavaScriptIn = null;
		Writer compressedJavaScriptOut = null;

		try {
			String sourceJavaScriptFilePath = repoDirectoryPath + javaScriptSourceDir + sourceJavaScriptFilename;
			sourceJavaScriptIn = new FileReader(sourceJavaScriptFilePath);
			JavaScriptCompressor javaScriptCompressor = new JavaScriptCompressor(sourceJavaScriptIn, errorReporter);
			compressedJavaScriptOut = new StringWriter();
			javaScriptCompressor.compress(compressedJavaScriptOut, -1, true, true, true, false);
			compressedContent = compressedJavaScriptOut.toString();

		} finally {

			if (null != sourceJavaScriptIn) {
				try {
					sourceJavaScriptIn.close();
				} catch (IOException ioe) {
					log.error("Error closing compression input reader", ioe);
				}
			}

			if (null != compressedJavaScriptOut) {
				try {
					compressedJavaScriptOut.close();
				} catch (IOException ioe) {
					log.error("Error closing compression output writer", ioe);
				}
			}
		}

		return compressedContent;

	}

	private void cloneRepo(String repoDirectoryPath) throws InvalidRemoteException, TransportException, GitAPIException {

		File repoDirectory = new File(repoDirectoryPath);

		if (repoDirectory.listFiles().length != 0) {
			log.debug("cloneRepo: " + repoDirectory + " is not empty");
			return;
		}

		log.debug("cloneRepo: Cloning repo from " + repoUrl);

		CloneCommand cloneCommand = new CloneCommand();

		Git git = cloneCommand.setURI(repoUrl).setDirectory(repoDirectory).setBranch("master").call();

		git.checkout().setName(repoTag).call();

		log.debug("cloneRepo: Cloned repo into " + repoDirectory + " and checked out " + repoTag);

	}

	private WorkDirectories initDirectories() throws IOException {

		String workDirCanonicalPath = makeDirectory(workDirPath);

		log.debug("Using temp directory: " + workDirCanonicalPath);

		String repoDirPath = workDirCanonicalPath + relativeRepoDirPath;
		String repoDirCanonicalPath = makeDirectory(repoDirPath);

		String buildDirPath = workDirCanonicalPath + relativeBuildDirPath;
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
