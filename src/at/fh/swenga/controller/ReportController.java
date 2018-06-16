package at.fh.swenga.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.GroceryDao;
import at.fh.swenga.model.Grocery;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class ReportController {

	@Autowired
	GroceryDao groceryDao;

	@RequestMapping(value = { "/report" })
	public String report(Model model, @RequestParam(required = false) String excel,
			@RequestParam(required = false) String pdf, @RequestParam(required = false) String mail
	/*
	 * @RequestParam(name = "grocier_id ", required = false) List<Integer>
	 * employeeIds
	 */) {

		/*
		 * User didn't select any employee ? -> go back to list if
		 * (CollectionUtils.isEmpty(employeeIds)) { model.addAttribute("errorMessage",
		 * "No employees selected!"); return "forward:/list"; }
		 */

		// Convert the list of ids to a list of employees.
		// The method findAll() can do this
		List<Grocery> groceries = groceryDao.findAll();

		// Store the employees in the model, so the reports can access them
		model.addAttribute("groceries", groceries);

		// Which submit button was pressed? -> call the right report view
		if (StringUtils.isNoneEmpty(excel)) {
			return "excelReport";
		} else if (StringUtils.isNoneEmpty(pdf)) {
			// return "pdfReport";
			return "pdfReport";
		} else if (StringUtils.isNoneEmpty(mail)) {
			// sendMail(groceries);
			model.addAttribute("errorMessage", "Mail sent");
			return "forward:/index";
		}

		else {
			return "forward:/index";
		}
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();
		return "error";
	}

}
