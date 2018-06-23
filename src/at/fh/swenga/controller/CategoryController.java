package at.fh.swenga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import at.fh.swenga.dao.CategoryDao;
import at.fh.swenga.model.Category;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class CategoryController {

	@Autowired
	CategoryDao categoryDao;

	List<Category> categories = new ArrayList<Category>();

	public CategoryController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@RequestMapping(value = "/addCategories")
	public String addCategories(Model model) {

		Category appointment = new Category("Appointment");
		categories.add(appointment);
		Category party = new Category("Party");
		categories.add(party);
		Category housekeeping = new Category("Housekeeping");
		categories.add(housekeeping);
		Category other = new Category("Other");
		categories.add(other);

		System.out.println(categories);

		for (Category category : categories) {
			categoryDao.save(category);
		}

		return "forward:login";
	}

}
