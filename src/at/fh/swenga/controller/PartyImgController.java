package at.fh.swenga.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sun.javafx.collections.MappingChange.Map;

import at.fh.swenga.dao.FlatDao;
import at.fh.swenga.dao.PartyImgDao;
import at.fh.swenga.dao.UserDao;
import at.fh.swenga.model.Image;
import at.fh.swenga.model.PartyImg;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class PartyImgController {

	@Autowired
	PartyImgDao partyImgDao;
	
	@Autowired
	FlatDao flatDao;
	@Autowired
	UserDao userDao;

	public PartyImgController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = { "showPartyImg" })
	public String showPartyImg(Model model,Authentication authentication) {

		List<PartyImg> partyimgs = partyImgDao.findAllByFlat_id(userDao.findFirstByUsername(authentication.getName()).getFlat().getId());
		List<String> picsAsString = new ArrayList<String>();
		
		HashMap<PartyImg, String> picMap = new HashMap<PartyImg, String>();
		
		/*for (PartyImg img : partyimgs) {
			byte[] pic = img.getImg();
			StringBuilder sb = new StringBuilder();
			sb.append("data:image/jpeg;base64,");
			sb.append(Base64.encodeBase64String(pic));
			
			String image = sb.toString();
			
			picsAsString.add(image);}
*/

		
		for (PartyImg img : partyimgs) {
			
			byte[] pic = img.getImg();
			StringBuilder sb = new StringBuilder();
			sb.append("data:image/jpeg;base64,");
			sb.append(Base64.encodeBase64String(pic));
			
			String image = sb.toString();
			
			picMap.put(img, image);

		}
		picMap.entrySet();
		
		
		model.addAttribute("picMap", picMap);
		
		
		model.addAttribute("partypics", picsAsString);
		model.addAttribute("partyimgs", partyimgs);
		

		return "listPartyPics";
	}

	@GetMapping(value = { "uploadPartyPic" })
	public String uploadPic(Model model) {
		return "uploadPartyPic";
	}

	@PostMapping(value = { "uploadPartyPic" })
	public String uploadPartyPic(Model model, @RequestParam("myFile") MultipartFile file,Authentication authentication,
			@RequestParam(value = "imgtitle",required = false) String imgtitle, @RequestParam(value = "imgtext",required = false) String imgtext) {

		try {
			PartyImg image = new PartyImg();
			image.setImg(file.getBytes());
			image.setFilename(file.getOriginalFilename());
			image.setUploadDate(Calendar.getInstance());
			image.setImgtitle(imgtitle);
			image.setImgtext(imgtext);
			image.setFlat(flatDao.findFirstByid(userDao.findFirstByUsername(authentication.getName()).getFlat().getId()));
			partyImgDao.save(image);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
			
		}

		return showPartyImg(model, authentication);
	}
	
	
	
	
	@RequestMapping("/deletePartyPic")
	public String deletePartyPic(Model model, @RequestParam int id, Authentication authentication) {
		partyImgDao.deleteById(id);

		return showPartyImg(model,authentication);
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();

		return "error";

	}

}