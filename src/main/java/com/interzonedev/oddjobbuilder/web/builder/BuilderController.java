package com.interzonedev.oddjobbuilder.web.builder;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interzonedev.oddjobbuilder.service.BuilderService;
import com.interzonedev.oddjobbuilder.web.OddJobBuilderController;

@Controller
@RequestMapping(value = "/builder")
public class BuilderController extends OddJobBuilderController {

	private static final String FORM_VIEW = "builder/form";
	private static final String RESULTS_VIEW = "builder/results";

	@Inject
	@Named("builderService")
	private BuilderService builderService;

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
			String compressedLibraryFilepath = builderService.buildLibrary(builderForm.isIncludeAjax(),
					builderForm.isIncludeLogger(), builderForm.isIncludeJQueryUtils());

			log.debug("buildLibrary: compressedLibraryFilepath = " + compressedLibraryFilepath);

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
