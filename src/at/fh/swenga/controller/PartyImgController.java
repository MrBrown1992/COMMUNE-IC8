package at.fh.swenga.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import at.fh.swenga.dao.PartyImgDao;
import at.fh.swenga.model.PartyImg;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class PartyImgController {

	@Autowired
	PartyImgDao partyImgDao;

	public PartyImgController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = { "showPartyImg" })
	public String showPartyImg(Model model) {

		List<PartyImg> partyimgs = partyImgDao.findAll();
		List<String> picsAsString = new ArrayList<String>();

		for (PartyImg img : partyimgs) {
			byte[] pic = img.getImg();
			StringBuilder sb = new StringBuilder();
			sb.append("data:image/jpeg;base64,");
			sb.append(Base64.encodeBase64String(pic));
			String image = sb.toString();
			picsAsString.add(image);

		}

		model.addAttribute("partypics", picsAsString);

		return "listPartyPics";
	}

	@GetMapping(value = { "uploadPartyPic" })
	public String uploadPic(Model model) {
		return "uploadPartyPic";
	}

	@PostMapping(value = { "uploadPartyPic" })
	public String uploadPartyPic(Model model, @RequestParam("myFile") MultipartFile file) {

		try {
			PartyImg image = new PartyImg();
			image.setImg(file.getBytes());
			image.setFilename(file.getOriginalFilename());
			image.setUploadDate(Calendar.getInstance());
			partyImgDao.save(image);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return showPartyImg(model);
	}

}