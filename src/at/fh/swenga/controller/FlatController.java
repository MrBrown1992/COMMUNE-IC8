package at.fh.swenga.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.FlatDao;
import at.fh.swenga.dao.UserDao;
import at.fh.swenga.model.Flat;


@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class FlatController {

	@Autowired
	FlatDao flatDao;

	@Autowired
	UserDao userDao;

	public FlatController() {

		// TODO Auto-generated constructor stub
	}

	private boolean errorsDetected(Model model, BindingResult bindingResult) {
		// Any errors? -> Create a String out of all errors and return to the page
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid";
			}
			model.addAttribute("errorMessage", errorMessage);
			return true;
		}
		return false;
	}

	@RequestMapping(value = { "listFlat" })
	public String listFlat(Model model) {

		List<Flat> flats = flatDao.findAll();		

		model.addAttribute("flats", flats);
		
		return "listFlat";
	}

	@RequestMapping(value = "addFlat")
	public String addFlat() {

		return "editFlat";
	}

	@RequestMapping(value = { "createNewFlat" })
	public String createNewFlat(Model model, @Valid Flat newFlat, @RequestParam(value = "name") String flatName,
			Authentication authentication) {

		newFlat.setName(flatName);

		flatDao.save(newFlat);

		return "forward:listFlat";
	}

	@RequestMapping(value = "editFlat")
	public String editFlat() {
		return "editFlat";
	}

	@PostMapping(value = "changeFlat")
	public String changeFlat(Model model, @RequestParam(value = "name") String name,
			@Valid Flat changedFlat, Authentication authentication, BindingResult bindingResult) {

		if (errorsDetected(model, bindingResult)) {
			return listFlat(model);
		}

		Flat flat = flatDao.findFirstByid(changedFlat.getId());
		if (flat != null) {
			flat.setName(name);

			flatDao.save(flat);

			return listFlat(model);
		} else {
			model.addAttribute("warningMessage", "Flat not found!");
			return listFlat(model);
		}
	}
	
	@GetMapping("/changeFlat")
	public String changeFlat( Model model, Authentication authentication, @RequestParam(value= "id")int id ) {

		Flat flat = flatDao.findFirstByid(id);

		if (flat != null) {

			model.addAttribute("flat", flat);
			return "editFlat";
		}

		model.addAttribute("warningMessage", "Flat not found!");
		return listFlat(model);
	}
	
	@RequestMapping("/deleteFlat")
	public String deleteFlat(Model model, @RequestParam int id) {
		flatDao.deleteById(id);

		return listFlat(model);
	}


}
