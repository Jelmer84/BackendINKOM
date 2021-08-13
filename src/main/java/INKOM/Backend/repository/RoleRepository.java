package INKOM.Backend.repository;

import INKOM.Backend.domain.ERole;
import INKOM.Backend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
