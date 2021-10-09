package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
    Optional<User> getByUsername(String username);
    @Query(nativeQuery = true,
            value = "select * from users u join roles r on (u.user_role_id=r.id) where not exists (select 1 from unconfirmed_users uu where uu.user_id=u.id)")
    ArrayList<User> getAllConfirmedUsers();
    ArrayList<User> findAllByUserRole(UserRole userRole);
}
