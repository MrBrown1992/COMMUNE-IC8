package at.fh.swenga.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.fh.swenga.dao.CommentDao;
import at.fh.swenga.dao.UserDao;
import at.fh.swenga.model.Comment;
import at.fh.swenga.model.Grocery;
import at.fh.swenga.model.User;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")

public class CommentController {

	@Autowired
	UserDao userDao;

	@Autowired
	CommentDao commentDao;

	public CommentController() {

		// TODO Auto-generated constructor stub
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

	@RequestMapping(value = { "/listComments" })
	public String listComments(Model model, Authentication authentication) {

		User user = userDao.findFirstByUsername(authentication.getName());
		//int flatId = user.getFlat().getId();
		//List<Comment> comments = commentDao.findAllByUser_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());
		
		List<Comment> comments = commentDao.findAllByFlat(user.getFlat());
		
		
		model.addAttribute("comments", comments);

		
		return "listComments";
	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName(Authentication authentication) {

		return authentication.getName();

	}

	@Secured({ "ROLE_USER" })
	@RequestMapping(value = "/addComment")
	public String addComment() {

		return "addComment";
	}

	@RequestMapping("/createComment")
	public String createComment(Model model, @Valid Comment newComment,
			@RequestParam(value = "commentText") String commentText, Authentication authentication,
			BindingResult bindingResult) {

		Date date = new Date();

		String username = authentication.getName();
		User user = userDao.findFirstByUsername(username);

		// Any errors? -> Create a String out of all errors and return to the page
		if (errorsDetected(model, bindingResult)) {
			return listComments(model, authentication);
		}

		
		newComment.setUser(user);
		newComment.setDate(date);
		newComment.setText(commentText);
		newComment.setFlat(user.getFlat());

		commentDao.save(newComment);

		return "forward:listComments";
	}


	
	@PostMapping("/changeComment")
	public String changeComment(Model model, @RequestParam(value="user", required = false) User user, @RequestParam(value="date", required=false) Date date, @RequestParam(value="commentText") String commentText, @Valid Comment changedComment, Authentication authentication, BindingResult bindR ) {
		
		if (errorsDetected(model, bindR)) {
			return listComments(model, authentication);
		}
		
		
		Comment comment = commentDao.findFirstByid(changedComment.getId());
		
		if (comment != null) {

			comment.setText(commentText);
			comment.setUser(user);
			comment.setDate(new Date());
			commentDao.save(comment);

			return listComments(model, authentication);
		} else {
			model.addAttribute("warningMessage", "Comment not found!");
			return listComments(model, authentication);}
		
	}

	@GetMapping("/changeComment")
	public String changeComment(@RequestParam(value = "id") int id, Model model, Authentication authentication) {
	
		Comment comment = commentDao.findFirstByid(id);

		if (comment != null) {

			model.addAttribute("comment", comment);
			return "addComment";
		}

		model.addAttribute("warningMessage", "Comment not found!");
		return listComments(model, authentication);
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/deleteComment")
	public String deleteComment(Model model, @RequestParam int id, Authentication authentication) {
		commentDao.deleteById(id);

		return listComments(model, authentication);
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();
		return "error";

	}

}
