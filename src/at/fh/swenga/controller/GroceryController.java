package at.fh.swenga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import at.fh.swenga.dao.GroceryDao;

@Controller
public class GroceryController {
	
	
	@Autowired 
	GroceryDao groceryDao;
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
	
	
	@GetMapping("/addGrocery")
	public String showAddGroceryForm(Model model) {
		return "editGrocery";
	}

}
