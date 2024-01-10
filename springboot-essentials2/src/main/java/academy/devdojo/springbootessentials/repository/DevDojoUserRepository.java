package academy.devdojo.springbootessentials.repository;

import academy.devdojo.springbootessentials.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevDojoUserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}