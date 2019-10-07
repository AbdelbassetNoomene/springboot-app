package tn.training.cni.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tn.training.cni.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTokenDTO {
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private List<Role> roles = new ArrayList<>();

}