package at.fh.swenga.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.UserDao;
import at.fh.swenga.dao.UserRoleDao;
import at.fh.swenga.model.Flat;
import at.fh.swenga.model.User;
import at.fh.swenga.model.UserRole;
@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class SecurityController {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao userRoleDao;

	/// UserRole userRole;
	
	
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String handleLogin() {
		return "login";
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
		User spiess = new User("spiess", "password", true, "nikolaus", "spiess", 0, "testmail@mimimi.com", null, null);
		spiess.encryptPassword();
		spiess.addUserRole(userRole);
		spiess.addUserRole(adminRole);
		spiess.setFlat(FlatController.testFlat);
		userDao.save(spiess);

		User user = new User("user", "password", true, "user", "user", 1, null, null, null);
		user.encryptPassword();
		user.addUserRole(userRole);
		userDao.save(user);

		return "forward:login";
	}
	
	
	 

	@RequestMapping("/addNewUser")
	@Transactional
	public String addNewUser(@Valid User newUser, @RequestParam(value = "userName") String userName,
			@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "password") String password, @RequestParam(value = "email") String email,
			/* @RequestParam(value = "dob") Date dob, */@RequestParam(value = "mobilenumber") int mobilenumber,
			@RequestParam(value ="Admin",required = false) UserRole userRole, @RequestParam(value = "flat") Flat flat,
			Authentication authentication, Model model, BindingResult bindingResult) {

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return ("/index");
		}
		
		
		
		

		newUser.setFirstname(firstName);
		newUser.setLastname(lastName);
		newUser.setUsername(userName);
		newUser.setPassword(password);
		newUser.encryptPassword();
		// newUser.setBirthdate(dob);
		newUser.setEmail(email);
		
		newUser.setMobilenumber(mobilenumber);
		// newUser.setFlat(flat);
		
		if ("Admin".equals(userRole)) {
			newUser.addUserRole(userRoleDao.findFirstByRoleName("ROLE_ADMIN"));
			newUser.addUserRole(userRoleDao.findFirstByRoleName("ROLE_USER"));

		}else {
			newUser.addUserRole(userRoleDao.findFirstByRoleName("ROLE_USER"));

		}
		
		newUser.setFlat(flat);
		

		userDao.save(newUser);

		return "listUsers";
	}

	@RequestMapping(value = { "/editUser" })
	public String user(Model model) {
		return "editUser";
	}
	
	@RequestMapping(value = { "/listUsers" })
	public String listUsers(Model model) {
		
		List<User> users = userDao.findAll();

		model.addAttribute("users", users);
		
		return "listUsers";
	}

	
	
	@RequestMapping(value = { "/" })
	public String index(Model model) {
		return "index";
	}

	

	public String getCurrentUser(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		model.addAttribute("name", name);
		return name;
	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadForm(Model model, @RequestParam("id") int id) {
		model.addAttribute("id",id);
		return "uploadFile";
	}
	
	
	

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();

		return "error";

	}

}
