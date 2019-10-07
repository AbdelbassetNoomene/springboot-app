package tn.training.cni.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String login    = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();

            log.info("Authentification for user: {}", login + " & password : {}" + password);

            List<User> userList;
            List<User> userEntity = new ArrayList<>();

            if (login.contains("@")) {
                userList = userRepository.findByEmail(login);
            } else {
                userList = userRepository.findByUsername(login);
            }
            LOGGER.info("userList size: " + userList.size());

            for (User user: userList) {

                LOGGER.info("user: " + user.toString());
                if (passwordEncoder.matches(password, user.getPassword()) || password.equals(user.getPassword())) {
                    userEntity.add(user);
                }
            }

            if (userEntity.isEmpty()) {
                Utils.throwRestException(RestExceptionCode.USERNAME_PW_NOT_MATCH, messageByLocaleService);
            }
            UserDTO authentificationResponseDTO = new UserDTO();
            List<Role> autoritiesList           = new ArrayList<>(userEntity.get(0).getRoles());
            User user                           = userEntity.get(0);
            if (!user.isEnabled()) {
                throw new RestException(RestExceptionCode.USER_NOT_ENABLED);
            }
            LOGGER.info("Auser: {}", user.toString());

            for (Role authEntity: autoritiesList) {
                authentificationResponseDTO.addRole(authEntity.getAuthority().trim());
                authentificationResponseDTO.addAuthority(new SimpleGrantedAuthority(authEntity.getAuthority().trim()));
            }
            appUserStorage.storeAppUser(authentificationResponseDTO);

            return new UsernamePasswordAuthenticationToken(authentificationResponseDTO, null, authentificationResponseDTO.getAuthorities());

        } finally {
            AppUserStorage.lock.unlock();
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
