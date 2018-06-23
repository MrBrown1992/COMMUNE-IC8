package at.fh.swenga.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.CategoryDao;
import at.fh.swenga.dao.FlatDao;
import at.fh.swenga.dao.TodoDao;
import at.fh.swenga.dao.UserDao;
import at.fh.swenga.model.Todo;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class TodoController {

	@Autowired
	TodoDao todoDao;
	
	@Autowired
	CategoryDao categoryDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	FlatDao flatDao;

	//private List<String> categories = new ArrayList<String>();


	public TodoController() {

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

	@RequestMapping(value = { "listTodos" })
	public String listTodos(Model model,Authentication authentication) {

		List<Todo> todos = todoDao.findAllByFlat_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());
		
		model.addAttribute("todos", todos);
		return "listTodo";
	}

	@RequestMapping(value = "/addTodo")
	@Transactional
	public String addTodo(Model model, @Valid Todo newTodo, @RequestParam(value = "name") String todoName,
			@RequestParam(value = "todoCategory") int todoCategory,
			@RequestParam(value = "date") String todoDate, Authentication authentication,
			BindingResult bindingResult) throws ParseException {

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return listTodos(model ,authentication);
		}
		model.addAttribute("categories", categoryDao.findAll());
		newTodo.setFlat(flatDao.findFirstByid(userDao.findFirstByUsername(authentication.getName()).getFlat().getId()));
		newTodo.setName(todoName);
		newTodo.setCategory(categoryDao.findFirstByid(todoCategory));
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Calendar date = Calendar.getInstance();
		date.setTime(format.parse(todoDate));
		newTodo.setDate(date);
		todoDao.save(newTodo);

		return listTodos(model,authentication);
	}

	@RequestMapping(value = { "/editTodo" })
	public String editTodo(Model model) {

		model.addAttribute("categories", categoryDao.findAll());

		return "editTodo";
	}

	@PostMapping("/changeTodo")
	public String changeTodo(Model model, @Valid Todo changedTodo,Authentication authentication,
			BindingResult bindingResult,@RequestParam(value = "todoCategory") int todoCategory,@RequestParam(value = "date") String dateAsString) throws ParseException {
		
		model.addAttribute("categories", categoryDao.findAll());
		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return listTodos(model,authentication);
		}

		
		Todo todo = todoDao.findFirstByid(changedTodo.getId());
		
		if (todo != null) {
			
			
			
			todo.setName(changedTodo.getName());

			todo.setCategory(categoryDao.findFirstByid(todoCategory)); // default value setzen ..
			todo.setDate(changedTodo.getDate());

			todoDao.save(todo);

			return listTodos(model,authentication);
		} else {
			model.addAttribute("warningMessage", "Todo not found!");
			return listTodos(model,authentication);
		}
	}

	@GetMapping("/changeTodo")
	public String changeTodo(@RequestParam(value = "id") int todo_id,  Model model, Authentication authentication) {
		
		model.addAttribute("categories", categoryDao.findAll());
		Todo todo = todoDao.findFirstByid(todo_id);
		
		if (todo != null) {
			model.addAttribute("todo", todo);
			return "editTodo";
		}

		model.addAttribute("warningMessage", "Todo not found!");
		return listTodos(model,authentication);
	}

	@RequestMapping("/deleteTodo")
	public String deleteTodo(Model model, @RequestParam int id,Authentication authentication) {
		todoDao.deleteById(id);

		return listTodos(model,authentication);
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();
		return "error";

	}
}
