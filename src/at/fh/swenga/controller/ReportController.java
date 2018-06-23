package at.fh.swenga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.CommentDao;
import at.fh.swenga.dao.GroceryDao;
import at.fh.swenga.dao.TodoDao;
import at.fh.swenga.dao.UserDao;
import at.fh.swenga.model.Comment;
import at.fh.swenga.model.Grocery;
import at.fh.swenga.model.Todo;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class ReportController {

	@Autowired
	GroceryDao groceryDao;

	@Autowired
	CommentDao commentDao;

	@Autowired
	TodoDao todoDao;
	@Autowired
	UserDao userDao;

	@RequestMapping(value = { "/groceryPdf" })
	public String groceryPdf(Model model, @RequestParam(required = false) String pdf, Authentication authentication) {

		List<Grocery> groceries = groceryDao
				.findAllByFlat_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());

		// Store the groceries in the model, so the reports can access them
		model.addAttribute("groceries", groceries);

		return "groceryPdfReport";

	}

	@RequestMapping(value = { "/groceryExcel" })
	public String excel(Model model, @RequestParam(required = false) String excel, Authentication authentication) {

		// The method findAll() can do this
		List<Grocery> groceries = groceryDao
				.findAllByFlat_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());

		// Store the groceries in the model, so the reports can access them
		model.addAttribute("groceries", groceries);

		return "groceryExcelReport";
	}

	@RequestMapping(value = { "/commentsPdf" })
	public String commentsPdf(Model model, @RequestParam(required = false) String pdf, Authentication authentication) {

		List<Comment> comments = commentDao
				.findAllByUser_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());

		// Store the comments in the model, so the reports can access them
		model.addAttribute("comments", comments);

		return "commentsPdfReport";

	}

	@RequestMapping(value = { "/commentsExcel" })
	public String commentsExcel(Model model, @RequestParam(required = false) String excel,
			Authentication authentication) {

		// The method findAll() can do this
		List<Comment> comments = commentDao
				.findAllByUser_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());

		// Store the comments in the model, so the reports can access them
		model.addAttribute("comments", comments);

		return "commentsExcelReport";
	}

	@RequestMapping(value = { "/todoPdf" })
	public String todoPdf(Model model, @RequestParam(required = false) String pdf, Authentication authentication) {

		List<Todo> todos = todoDao
				.findAllByFlat_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());

		// Store the todos in the model, so the reports can access them
		model.addAttribute("todos", todos);

		return "todoPdfReport";
	}

	@RequestMapping(value = { "/todoExcel" })
	public String todoExcel(Model model, @RequestParam(required = false) String excel, Authentication authentication) {

		// The method findAll() can do this
		List<Todo> todos = todoDao
				.findAllByFlat_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());

		// Store the todos in the model, so the reports can access them
		model.addAttribute("todos", todos);

		return "todoExcelReport";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();

		return "error";

	}
}
