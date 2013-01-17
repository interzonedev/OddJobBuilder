package com.interzonedev.oddjobbuilder.web.builder;

import java.io.File;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interzonedev.oddjobbuilder.web.OddJobBuilderController;

@Controller
@RequestMapping(value = "/builder")
public class BuilderController extends OddJobBuilderController {

	private static final String FORM_VIEW = "builder/form";
	private static final String RESULTS_VIEW = "builder/results";

	@RequestMapping(method = RequestMethod.GET)
	public String displayForm(Model model) {
		log.debug("displayForm: Start");

		model.addAttribute("builderForm", new BuilderForm());

		log.debug("displayForm: End");

		return FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String buildLibrary(Model model, BuilderForm builderForm, BindingResult bindingResult) {
		log.debug("buildLibrary: Start");

		try {
			// Call service
			String userDir = System.getProperty("user.dir");
			log.debug("userDir = " + userDir);

			File temp = new File(".");
			String absolutePath = temp.getAbsolutePath();
			String canonicalPath = temp.getCanonicalPath();
			String path = temp.getPath();
			boolean isAbsolute = temp.isAbsolute();
			boolean isDirectory = temp.isDirectory();
			boolean isFile = temp.isFile();

			log.debug("absolutePath = " + absolutePath);
			log.debug("canonicalPath = " + canonicalPath);
			log.debug("path = " + path);
			log.debug("isAbsolute = " + isAbsolute);
			log.debug("isDirectory = " + isDirectory);
			log.debug("isFile = " + isFile);

		} catch (Throwable t) {
			log.error("buildLibrary: Error building library", t);
			String errorMessage = t.getMessage();
			String stackTrace = ExceptionUtils.getStackTrace(t);
			bindingResult.reject("error.builder", new Object[] { errorMessage, stackTrace }, null);
			return FORM_VIEW;
		}

		// model.addAttribute("response", response);

		log.debug("buildLibrary: End");

		return RESULTS_VIEW;
	}

}
