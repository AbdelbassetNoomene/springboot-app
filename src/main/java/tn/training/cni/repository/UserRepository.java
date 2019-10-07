package tn.training.cni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.training.cni.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String mail);
}
