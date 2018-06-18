package at.fh.swenga.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.FlatDao;
import at.fh.swenga.model.Flat;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class FlatController {

	@Autowired
	FlatDao flatDao;

	public FlatController() {

		// TODO Auto-generated constructor stub
	}

	
	
	public  static Flat testFlat = new Flat("Admin WG");
	
	


	@RequestMapping(value = { "listFlat" })
public String listFlat(Model model) {

		List<Flat> flats = flatDao.findAll();

		model.addAttribute("flats", flats);
		return "listFlat";
	}

	@RequestMapping(value = "addFlat")
	public String addFlat() {

		return "editFlat";
	}

	@RequestMapping(value = { "createNewFlat" })
	public String createNewFlat(Model model, @Valid Flat newFlat, @RequestParam(value = "flatName") String flatName,
			Authentication authentication) {

		newFlat.setName(flatName);
		
		flatDao.save(newFlat);
		
		return "forward:listFlat";
	}

	@RequestMapping(value = "editFlat")
	public String editFlat() {
		return "editFlat";
	}
	
}
