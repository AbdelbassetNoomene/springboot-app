package tn.training.cni.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.ModelAndView;
import tn.training.cni.dto.UserDTO;
import tn.training.cni.model.User;
import tn.training.cni.service.UserService;
import tn.training.cni.utilities.GeneratePdfReport;


@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("")
	List<UserDTO>getListUsers() {
		return userService.getListUSers();
	}
	
	@GetMapping("/{id}")
	UserDTO getUserDetails(@PathVariable long id) {
		return userService.findById(id);
		
	}

	@GetMapping(value = "/pdfreport", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> usersReport() {
		List<UserDTO> users = userService.getListUSers();
		ByteArrayInputStream bis = GeneratePdfReport.usersReport(users);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping(value = "/pdfReport", produces = MediaType.APPLICATION_PDF_VALUE)
	public ModelAndView simpleReport() {
		List<UserDTO> users = userService.getListUSers();
		Map<String,Object> globalModel = new HashMap<>();
		globalModel.put("mode", "pdf");
		return new ModelAndView("simplePDF", globalModel);
	}

	@GetMapping(value = "/pdfHtmlreport", produces = MediaType.APPLICATION_PDF_VALUE)
	public ModelAndView usersHtmlReport() {
		List<UserDTO> users = userService.getListUSers();
		Map<String,Object> globalModel = new HashMap<>();
		globalModel.put("users", users);
		globalModel.put("mode", "pdf");
		return new ModelAndView("HTTMLPDF", globalModel);
	}

	@PostMapping("")
	//@PreAuthorize("hasRole('ADMIN')")
	UserDTO addUser(@RequestBody User user) throws Exception{
		return userService.saveUser(user);
	}
	
	@PutMapping("")
	UserDTO updateUser(@RequestBody User user) throws Exception {
		return userService.saveUser(user);
	}
	
	@DeleteMapping("/{id}")
	void deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
		
	}

}
