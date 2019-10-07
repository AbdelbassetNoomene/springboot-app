package tn.training.cni.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.training.cni.dto.UserDTO;
import tn.training.cni.model.Role;
import tn.training.cni.model.User;
import tn.training.cni.service.UserService;
import tn.training.cni.utilities.ConvertUtilities;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncode;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String login    = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();

            log.info("Authentification for user: {}", login + " & password : {}" + password);

            List<User> userList;
            List<User> userEntity = new ArrayList<>();
            User user = userService.findUserByMail(login);
            if (user == null) {
                throw new BadCredentialsException("Authentication failed");
            }
            if (this.passwordEncode.matches(password, user.getPassword()) || password.equals(user.getPassword())) {
               UserDTO userDto = ConvertUtilities.toUserDTO(user);
                return new UsernamePasswordAuthenticationToken(userDto, null, userDto.getAuthorities());
            }else{
                throw new BadCredentialsException("username and password don't match");
            }
        }catch (Exception e){
            throw new BadCredentialsException("Authentication failed");
        }
    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
