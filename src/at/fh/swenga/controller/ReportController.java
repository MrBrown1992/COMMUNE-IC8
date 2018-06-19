package at.fh.swenga.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.CommentDao;
import at.fh.swenga.dao.GroceryDao;
import at.fh.swenga.dao.TodoDao;
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

	@RequestMapping(value = { "/groceryPdf" })
	public String groceryPdf(Model model, @RequestParam(required = false) String pdf) {

		List<Grocery> groceries = groceryDao.findAll();

		// Store the groceries in the model, so the reports can access them
		model.addAttribute("groceries", groceries);

		if (StringUtils.isNoneEmpty(pdf)) {
			return "groceryPdfReport";
		} else {
			return "forward:/listGrocery";
		}
	}

	@RequestMapping(value = { "/groceryExcel" })
	public String excel(Model model, @RequestParam(required = false) String excel) {

		// The method findAll() can do this
		List<Grocery> groceries = groceryDao.findAll();

		// Store the groceries in the model, so the reports can access them
		model.addAttribute("groceries", groceries);

		if (StringUtils.isNoneEmpty(excel)) {
			return "groceryExcelReport";
		}

		else {
			return "forward:listGrocery";
		}
	}

	@RequestMapping(value = { "/commentsPdf" })
	public String commentsPdf(Model model, @RequestParam(required = false) String pdf) {

		List<Comment> comments = commentDao.findAll();

		// Store the comments in the model, so the reports can access them
		model.addAttribute("comments", comments);

		if (StringUtils.isNoneEmpty(pdf)) {
			return "commentsPdfReport";
		} else {
			return "forward:/listComments";
		}
	}

	@RequestMapping(value = { "/commentsExcel" })
	public String commentsExcel(Model model, @RequestParam(required = false) String excel) {

		// The method findAll() can do this
		List<Comment> comments = commentDao.findAll();

		// Store the comments in the model, so the reports can access them
		model.addAttribute("comments", comments);

		if (StringUtils.isNoneEmpty(excel)) {
			return "commentsExcelReport";
		}

		else {
			return "forward:listComments";
		}
	}

	@RequestMapping(value = { "/todoPdf" })
	public String todoPdf(Model model, @RequestParam(required = false) String pdf) {

		List<Todo> todos = todoDao.findAll();

		// Store the todos in the model, so the reports can access them
		model.addAttribute("todos", todos);

		if (StringUtils.isNoneEmpty(pdf)) {
			return "todoPdfReport";
		} else {
			return "forward:/listTodo";
		}
	}

	@RequestMapping(value = { "/todoExcel" })
	public String todoExcel(Model model, @RequestParam(required = false) String excel) {

		// The method findAll() can do this
		List<Todo> todos = todoDao.findAll();

		// Store the todos in the model, so the reports can access them
		model.addAttribute("todos", todos);

		if (StringUtils.isNoneEmpty(excel)) {
			return "todoExcelReport";
		}

		else {
			return "forward:listTodo";
		}
	}

}
