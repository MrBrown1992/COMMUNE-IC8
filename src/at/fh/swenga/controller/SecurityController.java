package at.fh.swenga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.fh.swenga.dao.UserDao;
import at.fh.swenga.dao.UserRoleDao;
import at.fh.swenga.model.User;
import at.fh.swenga.model.UserRole;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class SecurityController {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao userRoleDao;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String handleLogin() {
		return "login";
	}

	@RequestMapping("/addUser")
	@Transactional
	public String fillData(Model model) {

		UserRole adminRole = userRoleDao.findFirstByRoleName("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRole("ROLE_ADMIN");

		UserRole userRole = userRoleDao.findFirstByRoleName("ROLE_USER");
		if (userRole == null)
			userRole = new UserRole("ROLE_USER");

		// User admin = new User("admin", "password", true);
		User spiess = new User("spiess", "password", true, "admin", "admin", 0, null, null, null, null);
		spiess.encryptPassword();
		spiess.addUserRole(userRole);
		spiess.addUserRole(adminRole);
		userDao.save(spiess);

		User user = new User("user", "password", true, "user", "user", 1, null, null, null, null);
		user.encryptPassword();
		user.addUserRole(userRole);
		userDao.save(user);

		return "forward:login";
	}

	@RequestMapping(value = { "/" })
	public String index(Model model) {

		List<User> users = userDao.findAll();

		model.addAttribute("users", users);
		return "index";
	}

	@RequestMapping(value = { "/editGrocery" })
	public String grocery(Model model) {
		return "editGrocery";
	}

	@RequestMapping(value = { "/listGrocery" })
	public String listGrocery(Model model) {
		return "listGrocery";
	}


	
	public String getCurrentUser(Model model) {
		 
	      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName();
	      model.addAttribute("name", name);
	      return name;
	}
	
	
	
	
	
	
	
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();

		return "error";

	}

}
