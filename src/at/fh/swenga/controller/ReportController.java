package at.fh.swenga.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import at.fh.swenga.dao.GroceryDao;
import at.fh.swenga.model.Grocery;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class ReportController{

	@Autowired
	GroceryDao groceryDao;

	@RequestMapping(value = { "/pdf" })
	public String pdf(Model model,
			@RequestParam(required = false) String pdf
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
		if (StringUtils.isNoneEmpty(pdf)) {
			return "pdfReport";}
		else {
			return "forward:/listGrocery";
		}
	}
	
	
	@RequestMapping(value = { "/excel" })
	public String excel(Model model, @RequestParam(required = false) String excel
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
		}

		else {
			return "forward:listGrocery";
		}
	}

	

}
