package tn.training.cni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.training.cni.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRoleName(String roleName);

}
