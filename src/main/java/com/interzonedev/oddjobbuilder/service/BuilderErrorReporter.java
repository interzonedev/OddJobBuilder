package com.interzonedev.oddjobbuilder.service;

import javax.inject.Named;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

@Named("errorReporter")
public class BuilderErrorReporter implements ErrorReporter {

	private final Logger log = (Logger) LoggerFactory.getLogger(getClass());

	@Override
	public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {

		String errorMessage = getMessage(message, sourceName, line, lineSource, lineOffset);

		log.error(errorMessage);

	}

	@Override
	public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource,
			int lineOffset) {

		String errorMessage = getMessage(message, sourceName, line, lineSource, lineOffset);

		log.error(errorMessage);

		return new EvaluatorException(errorMessage);

	}

	@Override
	public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {

		String errorMessage = getMessage(message, sourceName, line, lineSource, lineOffset);

		log.warn(errorMessage);

	}

	private String getMessage(String message, String sourceName, int line, String lineSource, int lineOffset) {

		StringBuilder errorMessage = new StringBuilder();

		if (line >= 0) {
			errorMessage.append(line).append(":").append(lineOffset).append(":");
		}
		errorMessage.append(message);

		return errorMessage.toString();

	}
}
