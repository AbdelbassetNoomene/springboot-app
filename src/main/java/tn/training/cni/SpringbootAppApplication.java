package tn.training.cni;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.event.TransactionalEventListener;
import tn.training.cni.model.Role;
import tn.training.cni.model.User;
import tn.training.cni.repository.RoleRepository;
import tn.training.cni.repository.UserRepository;

import javax.servlet.http.HttpSessionEvent;
import java.util.Arrays;

@SpringBootApplication
public class SpringbootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAppApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner start(UserRepository userRepository, RoleRepository roleRepository) {
		return args -> {
			Role role1 = new Role(1L,"USER");
			Role role2 = new Role(2L,"ADMIN");
			roleRepository.save(role1);
			roleRepository.save(role2);

			userRepository.save(new User(1L,"test1@mail.com","test","Ahmed", "salah","22547874",null, Arrays.asList(role1)));
			userRepository.save(new User(2L,"test2@mail.com","test","Ali", "saidi","22547874",null,Arrays.asList(role1,role2)));
			userRepository.save(new User(3L,"test3@mail.com","test","Ammar", "slimani","22547874",null,Arrays.asList(role1)));
			userRepository.save(new User(4L,"test4@mail.com","test","Belgacem", "swilah","22547874",null,Arrays.asList(role1)));
			userRepository.save(new User(5L,"test5@mail.com","test","Nizar", "beji","22547874",null,Arrays.asList(role1,role2)));
			userRepository.save(new User(6L,"test6@mail.com","test","Anis", "saidani","22547874",null,Arrays.asList(role1)));
			userRepository.save(new User(7L,"test7@mail.com","test","Ameur", "salah","22547874",null,Arrays.asList(role1,role2)));
			userRepository.save(new User(8L,"test8@mail.com","test","Jabeur", "trabelsi","22547874",null,Arrays.asList(role1,role2)));

		};
	}

	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setService("http://192.168.2.7:9090/login/cas");
		serviceProperties.setSendRenew(false);
		return serviceProperties;
	}

	@Bean
	@Primary
	public AuthenticationEntryPoint authenticationEntryPoint(ServiceProperties sP) {
		CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
		entryPoint.setLoginUrl("http://auth.example.com:8080/cas/login");
		entryPoint.setServiceProperties(sP);
		return entryPoint;
	}

	@Bean
	public TicketValidator ticketValidator() {
		return new Cas30ServiceTicketValidator("http://auth.example.com:8080/cas");
	}

	@Bean
	public CasAuthenticationProvider casAuthenticationProvider() {
		CasAuthenticationProvider provider = new CasAuthenticationProvider();
		provider.setServiceProperties(serviceProperties());
		provider.setTicketValidator(ticketValidator());
		provider.setUserDetailsService((s) -> new org.springframework.security.core.userdetails.User("dwho", "dwho",
				true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
		provider.setKey("CAS_PROVIDER_LOCALHOST_9000");
		return provider;
	}


	@Bean
	public SecurityContextLogoutHandler securityContextLogoutHandler() {
		return new SecurityContextLogoutHandler();
	}

	@Bean
	public LogoutFilter logoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter(
				"http://auth.example.com:8080/cas/logout", securityContextLogoutHandler());
		logoutFilter.setFilterProcessesUrl("/logout/cas");
		return logoutFilter;
	}

	@Bean
	public SingleSignOutFilter singleSignOutFilter() {
		SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
		singleSignOutFilter.setCasServerUrlPrefix("http://auth.example.com:8080/cas");
		singleSignOutFilter.setIgnoreInitConfiguration(true);
		return singleSignOutFilter;
	}

	@EventListener
	public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener(HttpSessionEvent event) {
		return new SingleSignOutHttpSessionListener();
	}
}
