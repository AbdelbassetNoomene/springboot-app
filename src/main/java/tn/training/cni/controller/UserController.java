package tn.training.cni.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.training.cni.dto.UserDTO;
import tn.training.cni.model.User;
import tn.training.cni.service.UserService;


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
