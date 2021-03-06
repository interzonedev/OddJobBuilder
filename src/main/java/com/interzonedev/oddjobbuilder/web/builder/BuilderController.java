package com.interzonedev.oddjobbuilder.web.builder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interzonedev.oddjobbuilder.service.BuilderRequest;
import com.interzonedev.oddjobbuilder.service.BuilderResponse;
import com.interzonedev.oddjobbuilder.service.BuilderService;
import com.interzonedev.oddjobbuilder.web.OddJobBuilderController;

@Controller
@RequestMapping(value = "/builder")
public class BuilderController extends OddJobBuilderController {

    private static final String FORM_VIEW = "builder/form";

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

    @RequestMapping(method = RequestMethod.POST, params = "stats")
    public String getLibraryStats(Model model, BuilderForm builderForm, BindingResult bindingResult) {

        log.debug("getLibraryStats: Start");

        String view = null;

        try {

            BuilderRequest builderRequest = new BuilderRequest(builderForm.isIncludeAjax(),
                    builderForm.isIncludeLogger(), builderForm.isIncludeJQueryUtils());

            // Call service
            BuilderResponse builderResponse = builderService.buildLibrary(builderRequest);

            model.addAttribute("builderResponse", builderResponse);

            view = FORM_VIEW;

        } catch (Throwable t) {

            String errorMessage = getAndLogErrorMessageFromException(t);

            view = "redirect:/error?errorMessage=" + errorMessage;

        }

        log.debug("getLibraryStats: End");

        return view;

    }

    @RequestMapping(method = RequestMethod.POST, params = "download")
    public void downloadLibrary(Model model, BuilderForm builderForm, BindingResult bindingResult,
            HttpServletResponse response) {

        log.debug("downloadLibrary: Start");

        try {

            BuilderRequest builderRequest = new BuilderRequest(builderForm.isIncludeAjax(),
                    builderForm.isIncludeLogger(), builderForm.isIncludeJQueryUtils());

            // Call service
            BuilderResponse builderResponse = builderService.buildLibrary(builderRequest);

            String downloadFilename = builderResponse.getDownloadFilename();
            String compressedLibraryContents = builderResponse.getCompressedLibraryContents();

            log.debug("downloadLibrary: compressedLibraryContents = " + builderResponse.getCompressedLibraryContents());

            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + downloadFilename + "\"");

            response.getOutputStream().write(compressedLibraryContents.getBytes());
            response.flushBuffer();

        } catch (Throwable t) {

            String errorMessage = getAndLogErrorMessageFromException(t);

            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.addHeader("Location", "/error?errorMessage=" + errorMessage);

        }

        log.debug("downloadLibrary: End");

    }

    private String getAndLogErrorMessageFromException(Throwable t) {

        String errorMessage = "Error building library";
        log.error("downloadLibrary: " + errorMessage, t);
        if (StringUtils.isNotBlank(t.getMessage())) {
            errorMessage += " - " + t.getMessage();
        }
        return errorMessage;
    }
}
