package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.entity.user.UserAccessCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccessCodeRepository extends JpaRepository<UserAccessCode, Long> {
    UserAccessCode getByUser(User user);
    boolean existsByUser(User user);
    void deleteByUser(User user);
}
