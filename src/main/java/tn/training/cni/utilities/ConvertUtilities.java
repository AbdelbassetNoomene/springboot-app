package tn.training.cni.utilities;

import org.springframework.util.CollectionUtils;
import tn.training.cni.dto.UserDTO;
import tn.training.cni.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ConvertUtilities {

	public static UserDTO toUserDTO(User u) {
		UserDTO user = new UserDTO();
		user.setEmail(u.getEmail());
		user.setFirstname(u.getFirstname());
		user.setId(u.getId());
		user.setLastname(u.getLastname());
		user.setPhone(u.getPhone());
		user.setImage(u.getImage());
		List<String> roles = new ArrayList<>();
		u.getRoles().stream().forEach(r -> roles.add(r.getRoleName()));
		user.setRoles(roles);
		return user;
	}

	public static User toUser(UserDTO u) {
		User user = new User();
		user.setEmail(u.getEmail());
		user.setFirstname(u.getFirstname());
		user.setId(u.getId());
		user.setLastname(u.getLastname());
		user.setPhone(u.getPhone());
		user.setImage(u.getImage());
		return user;
	}

    public static List<UserDTO> toListUserDTO(List<User> users) {
        List<UserDTO> listUSers = new ArrayList<>();
	    if(!CollectionUtils.isEmpty(users)){
            users.stream().forEach(u->listUSers.add(toUserDTO(u)));
        }
	    return listUSers;
    }

}
