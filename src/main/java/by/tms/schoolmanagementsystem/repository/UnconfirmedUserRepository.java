package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.user.UnconfirmedUser;
import by.tms.schoolmanagementsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnconfirmedUserRepository extends JpaRepository<UnconfirmedUser, Long> {
    boolean existsByUser_Username(String username);
    UnconfirmedUser getByUser(User user);
    @Query("select uu.user from UnconfirmedUser uu")
    List<User> getAll();
    @Query("delete from UnconfirmedUser uu where uu.user.id=:id")
    void deleteByUserId(@Param(value = "id") long id);
    boolean existsByUser_Id(long id);
}
