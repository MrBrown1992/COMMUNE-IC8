package at.fh.swenga.controller;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.annotation.Secured;
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

		Flat testFlat = flatDao.findFirstByFlatName("Admin WG");
		if (testFlat == null) {
			testFlat = new Flat("Admin WG");

			flatDao.save(testFlat);
		}

		UserRole rootRole = userRoleDao.findFirstByRoleName("ROLE_ROOT");
		if (rootRole == null)
			rootRole = new UserRole("ROLE_ROOT");

		UserRole adminRole = userRoleDao.findFirstByRoleName("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRole("ROLE_ADMIN");

		UserRole userRole = userRoleDao.findFirstByRoleName("ROLE_USER");
		if (userRole == null)
			userRole = new UserRole("ROLE_USER");

		User root = new User("root", "password", true, "Master", "of the Universe", 3141592, "master@universe.xyz",
				Calendar.getInstance(), null, null);
		root.encryptPassword();
		root.addUserRole(userRole);
		root.addUserRole(adminRole);
		root.addUserRole(rootRole);
		root.setFlat(testFlat);
		userDao.save(root);

		User spiess = new User("spiess", "password", true, "Nikolaus", "Spiess", 0316123456, "testmail@mimimi.com",
				Calendar.getInstance(), null, null);
		spiess.encryptPassword();
		spiess.addUserRole(userRole);
		spiess.addUserRole(adminRole);
		spiess.setFlat(testFlat);
		userDao.save(spiess);

		User user = new User("user", "password", true, "user", "user", 0664123321, "malware@xyz.com",
				Calendar.getInstance(), null, null);
		user.encryptPassword();
		user.addUserRole(userRole);
		user.setFlat(testFlat);
		userDao.save(user);

		User steiner = new User("steiner", "password", true, "steiner", "steiner", 0664123321, "steiner@xyz.com",
				Calendar.getInstance(), null, null);
		steiner.encryptPassword();
		steiner.addUserRole(userRole);
		steiner.setFlat(testFlat);
		userDao.save(steiner);

		return "forward:login";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/addNewUser")
	@Transactional
	public String addNewUser(@Valid User newUser, @RequestParam(value = "username") String username,
			@RequestParam(value = "firstname") String firstname, @RequestParam(value = "lastname") String lastname,
			@RequestParam(value = "password") String password, @RequestParam(value = "email") String email,
			@RequestParam(value = "birthdate") String dobString, @RequestParam(value = "mobilenumber") int mobilenumber,
			@RequestParam(value = "Admin", required = false) boolean isAdmin,
			@RequestParam(value = "Root", required = false) boolean isRoot, @RequestParam(value = "flat") int flat_id,
			Authentication authentication, Model model, BindingResult bindingResult) throws ParseException {

		if (errorsDetected(model, bindingResult)) {
			return ("/index");
		}

		newUser.setFirstname(firstname);
		newUser.setLastname(lastname);
		newUser.setUsername(username);
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

		if (isRoot) {
			newUser.addUserRole(userRoleDao.findFirstByRoleName("ROLE_ROOT"));
		}

		userDao.save(newUser);

		return listUsers(model);
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = { "/editUser" })
	public String user(Model model) {
		model.addAttribute("flats", flatDao.findAll());
		return "editUser";
	}

	@PostMapping("/changeUser")
	public String changeUser(Model model, @RequestParam(value = "id") int id,@RequestParam(value = "birthdate") String dobString, @Valid User changedUser,
			Authentication authentication, BindingResult bindingResult) throws ParseException {
		
		
		model.addAttribute("flats", flatDao.findAll());
		
		if (errorsDetected(model, bindingResult)) {
			return listUsers(model);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Calendar dob = Calendar.getInstance();
		dob.setTime(format.parse(dobString));

		User user = userDao.findFirstByid(changedUser.getId());
		if (user != null) {
			user.setUsername(changedUser.getUsername());
			user.setPassword(changedUser.getPassword());
			user.setFirstname(changedUser.getFirstname());
			user.setLastname(changedUser.getLastname());
			user.setMobilenumber(changedUser.getMobilenumber());
			user.setEmail(changedUser.getEmail());
			user.setFlat(changedUser.getFlat());
			user.setUserRoles(changedUser.getUserRoles()); // <-- wie geht das :( ???d
			user.setBirthdate(changedUser.getBirthdate());
		
			userDao.save(user);

			return listUsers(model);
		} else {
			model.addAttribute("warningMessage", "User not found!");
			return listUsers(model);
		}
	}

	@GetMapping("/changeUser")
	public String changeUser(@RequestParam(value = "id") int id, Model model, Authentication authentication) {
		model.addAttribute("flats", flatDao.findAll());
		User user = userDao.findFirstByid(id);

		if (user != null) {

			model.addAttribute("user", user);
			return "editUser";
		}

		model.addAttribute("warningMessage", "User not found!");
		return listUsers(model);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = { "/listUsers" })
	public String listUsers(Model model) {

		List<User> users = userDao.findAll();

		model.addAttribute("users", users);

		return "listUsers";
	}

	@RequestMapping(value = { "/" })
	public String index(Model model, Authentication authentication) {

		String username = authentication.getName();
		User user = userDao.findFirstByUsername(username);
		String fullname = user.getFirstname() + " " + user.getLastname();
		model.addAttribute("fullname", fullname);
		model.addAttribute("user", user);

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
		}

		return "index";
	}

	@RequestMapping(value = { "/currentUser" })
	public String getCurrentUser(Model model, Authentication authentication) {

		String name = authentication.getName();
		String firstname = userDao.findFirstByUsername(name).getFirstname();
		String lastname = userDao.findFirstByUsername(name).getLastname();

		model.addAttribute("name", name);
		model.addAttribute("firstname", firstname);
		model.addAttribute("lastname", lastname);

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
				// Don't forget to remove the relationship
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

	@RequestMapping("/download")
	public void download(@RequestParam("documentId") int documentId, HttpServletResponse response) {

		Optional<Image> iamgeOpt = imageDao.findById(documentId);
		if (!iamgeOpt.isPresent())
			throw new IllegalArgumentException("No Image with id " + documentId);

		Image img = iamgeOpt.get();

		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + img.getFilename() + "\"");
			OutputStream out = response.getOutputStream();
			// application/octet-stream
			response.setContentType(img.getContentType());
			out.write(img.getImg());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = { "showUserProfile" })
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
		return "showUserProfile"; // <-- profil anzeigen :)
	}

	@Secured("ROLE_ROOT")
	@RequestMapping(value = "/deleteUser")
	public String deleteUser(Model model, @RequestParam int id) {
		userDao.deleteById(id);

		return listUsers(model);
	}

	@RequestMapping(value = "/about")
	public String about(Model model) {
		return "about";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();

		return "error";

	}

}
