package at.fh.swenga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import at.fh.swenga.dao.CommentDao;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")

public class CommentController {

	@Autowired
	CommentDao commentDao;

	/**
	 * 
	 */
	public CommentController() {
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();
		return "error";

	}
}
