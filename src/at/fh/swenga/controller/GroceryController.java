package at.fh.swenga.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.GroceryDao;
import at.fh.swenga.model.Grocery;


@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class GroceryController {

	@Autowired
	 GroceryDao groceryDao;

	public GroceryController() {

		// TODO Auto-generated constructor stub
	}

	private PageRequest generatePageRequest(int pageNr) {
		return PageRequest.of(pageNr, 6);
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
	
	

	
	@RequestMapping(value = {"showGroceryList"})
	public String showGroceryList(Model model) {
		

		List<Grocery> groceries = groceryDao.findAll();

		model.addAttribute("groceries",groceries);
		return "listGrocery";
	}

	
	@RequestMapping("/createNewGroceryItem")
	public String createNewGroceryItem(Model model, @Valid Grocery newGrocery,
			@RequestParam(value = "groceryName") String groceryName,
			@RequestParam(value = "boughtstate") boolean bought, Authentication authentication,
			BindingResult bindingResult) {

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return showGroceryList(model);
		}

		newGrocery.setName(groceryName);
		newGrocery.setBought(bought);
		groceryDao.save(newGrocery);

		return showGroceryList(model);
	}

	@PostMapping("/changeGrocery")
	public String changeGrocery(Model model,	@RequestParam(value = "groceryName") String groceryName,
			@RequestParam(value = "boughtstate",required = false,defaultValue = "false" ) boolean bought,
			
			@Valid Grocery changedGrocery, Authentication authentication,
			BindingResult bindingResult) {

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return showGroceryList(model);
		}

		Grocery grocery = groceryDao.findFirstByid(changedGrocery.getId());
		if (grocery != null) {
			grocery.setName(groceryName);
			
			grocery.setBought(bought); // default valu setzen .. 
			

			groceryDao.save(grocery);

			return showGroceryList(model);
		} else {
			model.addAttribute("warningMessage", "Grocery not found!");
			return showGroceryList(model);
		}
	}
	
	@GetMapping("/changeGrocery")
	public String changeGrocery(@RequestParam int id ,Model model ,Authentication authentication ) {
		
		
		Grocery grocery = groceryDao.findFirstByid(id);
		
		if(grocery != null ) {
			
			model.addAttribute("grocery",grocery);
			return "editGrocery"; 
		}
		
		model.addAttribute("warningMessage", "Grocery not found!");
		return showGroceryList(model);
	}
	
	
	@RequestMapping("/delete")
	public String deleteGrocery(Model model, @RequestParam int id) {
		groceryDao.deleteById(id);

		return showGroceryList(model);
	}
	
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();
		return "error";

	}
}
