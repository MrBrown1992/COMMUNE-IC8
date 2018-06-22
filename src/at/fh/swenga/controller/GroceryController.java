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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.FlatDao;
import at.fh.swenga.dao.GroceryDao;
import at.fh.swenga.dao.UserDao;
import at.fh.swenga.model.Flat;
import at.fh.swenga.model.Grocery;
import at.fh.swenga.model.User;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class GroceryController {

	@Autowired
	GroceryDao groceryDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	FlatDao flatDao;
	
	public GroceryController() {

		// TODO Auto-generated constructor stub
	}

	private boolean errorsDetected(Model model, BindingResult bindingResult) {

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

	@RequestMapping(value = { "showGroceryList" })
	public String showGroceryList(Model model,Authentication authentication) {
				
		List<Grocery> groceries = groceryDao.findAllByFlat_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());

		model.addAttribute("groceries", groceries);
		return "listGrocery";
	}

	@RequestMapping("/createNewGroceryItem")
	public String createNewGroceryItem(Model model, @Valid Grocery newGrocery,
			@RequestParam(value = "groceryName") String groceryName,
			@RequestParam(value = "boughtstate", required = false) boolean bought, Authentication authentication,
			BindingResult bindingResult) {

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return showGroceryList(model,authentication);
		}

	
		
		newGrocery.setName(groceryName);
		newGrocery.setBought(bought);
		newGrocery.setFlat(flatDao.findFirstByid(userDao.findFirstByUsername(authentication.getName()).getFlat().getId())); // Peter Salhofer wäre stolz ;) 
		groceryDao.save(newGrocery);

		return showGroceryList(model,authentication);
	}

	
	
	
	@PostMapping("/changeGrocery")
	public String changeGrocery(Model model, @RequestParam(value = "groceryName") String groceryName,
			@RequestParam(value = "boughtstate", required = false, defaultValue = "false") boolean bought,
			@Valid Grocery changedGrocery, Authentication authentication, BindingResult bindingResult) {

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return showGroceryList(model,authentication);
		}

		Grocery grocery = groceryDao.findFirstByid(changedGrocery.getId());
		if (grocery != null) {
			grocery.setName(groceryName);

			grocery.setBought(bought); // default value setzen ..

			groceryDao.save(grocery);

			return showGroceryList(model,authentication);
		} else {
			model.addAttribute("warningMessage", "Grocery not found!");
			return showGroceryList(model,authentication);
		}
	}

	@GetMapping("/changeGrocery")
	public String changeGrocery(@RequestParam(value = "id") int id, Model model, Authentication authentication) {

		Grocery grocery = groceryDao.findFirstByid(id);

		if (grocery != null) {

			model.addAttribute("grocery", grocery);
			return "editGrocery";
		}

		model.addAttribute("warningMessage", "Grocery not found!");
		return showGroceryList(model,authentication);
	}

	@RequestMapping("/delete")
	public String deleteGrocery(Model model, @RequestParam int id, Authentication authentication) {
		groceryDao.deleteById(id);

		return showGroceryList(model,authentication);
	}

	@RequestMapping(value = { "/editGrocery" })
	public String grocery(Model model) {
		return "editGrocery";
	}

	@RequestMapping(value = { "/listGrocery" })
	public String listGrocery(Model model) {
		return "listGrocery";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();
		return "error";

	}
}
