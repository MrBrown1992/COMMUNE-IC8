package at.fh.swenga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ExceptionHandler;

import at.fh.swenga.dao.UserRoleDao;
import at.fh.swenga.dao.UserDao;

import at.fh.swenga.model.User;
import at.fh.swenga.model.UserRole;

@Controller
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

		UserRole adminRole = userRoleDao.getRole("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRole("ROLE_ADMIN");

		UserRole userRole = userRoleDao.getRole("ROLE_USER");
		if (userRole == null)
			userRole = new UserRole("ROLE_USER");

		User admin = new User("admin", "password", true);
		User spiess = new User("spiess", "password", true, null, null, 0, null, null, null, null);
		spiess.encryptPassword();
		spiess.addUserRole(userRole);
		spiess.addUserRole(adminRole);
		userDao.persist(spiess);
		
		User user = new User("user", "password", true);
		user.encryptPassword();
		user.addUserRole(userRole);
		userDao.persist(user);
		

		return "forward:login";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}

}
