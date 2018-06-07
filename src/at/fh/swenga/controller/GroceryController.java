package at.fh.swenga.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	
	@GetMapping("/addGrocery")
	public String showAddGroceryForm(Model model) {
		return "editGrocery";
		
	} /*
	@PostMapping("/addGrocery")
	public String addGrocery(@Valid Grocery grocery, BindingResult bindingResult, Model model) {

		//kein plan
		return null ;
	}*/
}
