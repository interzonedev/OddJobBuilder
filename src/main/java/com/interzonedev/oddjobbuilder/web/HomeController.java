package com.interzonedev.oddjobbuilder.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class HomeController extends OddJobBuilderController {

	@RequestMapping(method = RequestMethod.GET)
	public String displayForm(Model model) {

		return "redirect:/builder";

	}

}
