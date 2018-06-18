package at.fh.swenga.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.TodoDao;
import at.fh.swenga.model.Grocery;
import at.fh.swenga.model.Todo;
import at.fh.swenga.model.User;
import at.fh.swenga.model.UserRole;


@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class TodoController {

	@Autowired
	TodoDao todoDao;

	public TodoController() {

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

	
	
	
	@RequestMapping(value = {"listTodos"})
	public String listTodos(Model model) {
		

		List<Todo> todos = todoDao.findAll();

		model.addAttribute("todos",todos);
		return "listTodo";
	}

	
	@RequestMapping(value="/addTodo") 
	public String addTodo(Model model, @Valid Todo newTodo,
			@RequestParam(value = "todoName") String todoName,
			@ModelAttribute(value = "todoCategory") String todoCategory, @RequestParam(value = "todoDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date todoDate, Authentication authentication,
			BindingResult bindingResult) {
		
		List<String> categories = new ArrayList<String>();
		categories.add("Appointment");
		categories.add("Cleaning");
		categories.add("Housekeeping");
		categories.add("Party");
		categories.add("Other");

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return listTodos(model);
		}

		newTodo.setName(todoName);
		newTodo.setCategory(todoCategory);
		newTodo.setDate(todoDate);
		todoDao.save(newTodo);

		return listTodos(model);
	}

	
	
	@RequestMapping(value = { "/editTodo" })
	public String editTodo(Model model) {
		return "editTodo";
	}
	
	
	@PostMapping("/changeTodo")
	public String changeTodo(Model model,	@RequestParam(value = "todoName") String todoName,
			@RequestParam(value = "category") String category, @RequestParam(value = "date") Date date,
			
			@Valid Todo changedTodo, Authentication authentication,
			BindingResult bindingResult) {

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return listTodos(model);
		}

		Todo todo = todoDao.findFirstByid(changedTodo.getId());
		if (todo != null) {
			todo.setName(todoName);
			
			todo.setCategory(category); // default value setzen .. 
			todo.setDate(date);

			todoDao.save(todo);

			return listTodos(model);
		} else {
			model.addAttribute("warningMessage", "Todo not found!");
			return listTodos(model);
		}
	}
	
	@GetMapping("/changeTodo")
	public String changeTodo(@RequestParam(value = "id") int id ,Model model ,Authentication authentication ) {
		
		
		Todo todo = todoDao.findFirstByid(id);
		
		if(todo != null ) {
			
			model.addAttribute("todo",todo);
			return "editTodo"; 
		}
		
		model.addAttribute("warningMessage", "Todo not found!");
		return listTodos(model);
	}
	
	
	@RequestMapping("/deleteTodo")
	public String deleteTodo(Model model, @RequestParam int id) {
		todoDao.deleteById(id);

		return listTodos(model);
	}
	
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();
		return "error";

	}
}
