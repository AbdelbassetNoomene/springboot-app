package tn.training.cni;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.training.cni.model.Role;
import tn.training.cni.model.User;
import tn.training.cni.repository.RoleRepository;
import tn.training.cni.repository.UserRepository;

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

}
