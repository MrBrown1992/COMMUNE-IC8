package at.fh.swenga.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.GroceryDao;
import at.fh.swenga.model.Grocery;

@Controller
public class GroceryController {
	
	
	@Autowired 
	GroceryDao groceryDao;
	/*
	@Autowired
	Grocery grocery;
	*/
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
	
	
	@RequestMapping(value = "/addGrocery" , method = RequestMethod.GET)
	public String showAddGroceryForm(Model model) {
		return "editGrocery";
		
	}
	
	@RequestMapping(value = "/addGrocery", method = RequestMethod.POST)
	public String addGrocery(@Valid @ModelAttribute Grocery newGrocery, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/listEmployees";
		}

		Grocery grocery = GroceryDao.getGroceryById(newGrocery.getId());

		if (grocery != null) {
			model.addAttribute("errorMessage", "Grocery already exists!<br>");
		} else {
			GroceryDao.addGrocery(newGrocery);
			model.addAttribute("message", "New grocery " + newGrocery.getId() + " added.");
		}

		return "forward:/listGrocery";
	}

	@RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
	public String showChangeGroceryForm(Model model, @RequestParam int id) {
		Grocery grocery = GroceryDao.getGroceryById(id);
		if (grocery != null) {
			model.addAttribute("grocery", grocery);
			return "editGrocery";
		} else {
			model.addAttribute("errorMessage", "Couldn't find grocery " + id);
			return "forward:/listGrocery";
		}
	}

	@RequestMapping(value = "/editGrocery", method = RequestMethod.POST)
	public String changeEmployee(@Valid @ModelAttribute Grocery changedGrocery, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/listGrocery";
		}

		Grocery grocery = GroceryDao.getGroceryById(changedGrocery.getId());

		if (grocery == null) {
			model.addAttribute("errorMessage", "Grocery does not exist!<br>");
		} else {
			grocery.setId(changedGrocery.getId());
			grocery.setName(changedGrocery.getName());
			grocery.setBought(changedGrocery.isBought());
			
			model.addAttribute("message", "Changed grocery " + changedGrocery.getId());
		}

		return "forward:/listGrocery";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	@PostMapping("/addGrocery")
	public String addGrocery(@Valid Grocery grocery, BindingResult bindingResult, Model model) {

		//kein plan
		return null ;
	}*/
	
	
	
}
