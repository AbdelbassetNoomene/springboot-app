package tn.training.cni.service;

import tn.training.cni.dto.UserDTO;
import tn.training.cni.model.Role;
import tn.training.cni.model.User;

import java.io.IOException;
import java.util.List;


public interface UserService {
	
	UserDTO saveUser(User user) throws IOException ;

	UserDTO updateUser(UserDTO user) throws IOException ;

	UserDTO findById(long id);

	void deleteUser(long id);

	Role saveRole(Role role);

	void addRoleToUser(String mail, String roleName);

	User findUserByMail(String login);

	List<UserDTO> getListUSers();

}
