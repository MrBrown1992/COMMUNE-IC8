package at.fh.swenga.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
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
import org.springframework.web.multipart.MultipartFile;

import at.fh.swenga.dao.FlatDao;
import at.fh.swenga.dao.ImageDao;
import at.fh.swenga.dao.UserDao;
import at.fh.swenga.dao.UserRoleDao;
import at.fh.swenga.model.Flat;
import at.fh.swenga.model.Image;
import at.fh.swenga.model.User;
import at.fh.swenga.model.UserRole;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class SecurityController {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao userRoleDao;

	@Autowired
	ImageDao imageDao;

	@Autowired
	FlatDao flatDao;

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
		User spiess = new User("spiess", "password", true, "nikolaus", "spiess", 0316666666, "testmail@mimimi.com",
				Calendar.getInstance(), null, null);
		spiess.encryptPassword();
		spiess.addUserRole(userRole);
		spiess.addUserRole(adminRole);
		spiess.setFlat(FlatController.testFlat);
		userDao.save(spiess);

		User user = new User("user", "password", true, "user", "user", 1, null, Calendar.getInstance(), null, null);
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
			@RequestParam(value = "dob") String dobString, @RequestParam(value = "mobilenumber") int mobilenumber,
			@RequestParam(value = "Admin", required = false) boolean isAdmin, @RequestParam(value = "flat") int flat_id,
			Authentication authentication, Model model, BindingResult bindingResult) throws ParseException {

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return ("/index");
		}

		newUser.setFirstname(firstName);
		newUser.setLastname(lastName);
		newUser.setUsername(userName);
		newUser.setPassword(password);
		newUser.encryptPassword();
		newUser.setEnabled(true);
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Calendar dob = Calendar.getInstance();
		dob.setTime(format.parse(dobString));
		newUser.setBirthdate(dob);
		newUser.setEmail(email);

		newUser.setMobilenumber(mobilenumber);
		newUser.setFlat(flatDao.findFirstByid(flat_id));

		newUser.addUserRole(userRoleDao.findFirstByRoleName("ROLE_USER"));
		if (isAdmin) {
			newUser.addUserRole(userRoleDao.findFirstByRoleName("ROLE_ADMIN"));
		}

		userDao.save(newUser);

		return listUsers(model);
	}

	@RequestMapping(value = { "/editUser" })
	public String user(Model model) {
		model.addAttribute("flats", flatDao.findAll());
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
	public String showUploadForm(Model model, @RequestParam("id") int id, Authentication authentication) {

		String firstname = userDao.findFirstByid(id).getFirstname();
		String lastname = userDao.findFirstByid(id).getLastname();
		model.addAttribute("id", id);
		model.addAttribute("firstname", firstname);
		model.addAttribute("lastname", lastname);

		return "uploadFile";

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDocument(Model model, @RequestParam("id") String id1,
			@RequestParam("myFile") MultipartFile file) {

		int id = Integer.parseInt(id1);
		try {

			Optional<User> userOpt = userDao.findById(id);
			if (!userOpt.isPresent())
				throw new IllegalArgumentException("No employee with id " + id);

			User user = userOpt.get();

			// Already a document available -> delete it
			if (user.getUserimage() != null) {
				imageDao.delete(user.getUserimage());
				// Don't forget to remove the relationship too
				user.setUserimage(null);
			}

			// Create a new document and set all available infos

			Image image = new Image();
			image.setImg(file.getBytes());
			image.setFilename(file.getOriginalFilename());
			image.setAttacheduser(user);
			user.setUserimage(image);
			userDao.save(user);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return listUsers(model);
	}

	// @Secured({ "ROLE_USER" })
	@RequestMapping(value = { "showProfile" })
	public String showProfile(Model model, Authentication authentication) {

		User user = userDao.findFirstByUsername(authentication.getName());

		if (user != null && user.isEnabled()) {

			model.addAttribute("user", user);
			if (user.getUserimage() != null) {

				Optional<Image> imageOpt = imageDao.findById(user.getUserimage().getId());
				Image img = imageOpt.get();
				byte[] profilePicture = img.getImg();

				StringBuilder sb = new StringBuilder();
				sb.append("data:image/jpeg;base64,");
				sb.append(Base64.encodeBase64String(profilePicture));
				String image = sb.toString();

				model.addAttribute("image", image);
			}
		} else {
			model.addAttribute("errorMessage", "Something went wrong!");
			return "login";
		}
		return "showProfile"; // <-- pofil anzeigen :)
	}

	@RequestMapping(value = "/deleteUser")
	public String deleteUser(Model model, @RequestParam int id) {
		userDao.deleteById(id);

		return listUsers(model);
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();

		return "error";

	}

}
