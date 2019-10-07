package tn.training.cni.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String phone;
	private String image;
	private List<String> roles = new ArrayList<String>();
	@JsonIgnore
	private List<GrantedAuthority> authorities;

	public UserDTO(String email, String password, List<GrantedAuthority> authorities) {
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		authorities.stream().forEach(auth->this.roles.add(auth.getAuthority()));
	}
}
