package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.mark.Mark;
import by.tms.schoolmanagementsystem.entity.user.UnconfirmedUser;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.entity.user.UserInfo;
import by.tms.schoolmanagementsystem.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UnconfirmedUserRepository unconfirmedUserRepository;
    private RoleRepository roleRepository;
    private MarkRepository markRepository;
    private LessonRepository lessonRepository;
    private HomeworkRepository homeworkRepository;

    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username){
        if(unconfirmedUserRepository.existsByUser_Username(username)){
            return Optional.empty();
        } else {
            return userRepository.getByUsername(username);
        }
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByUsernameAndPassword(String username, String password){
        return userRepository.existsByUsernameAndPassword(username, password);
    }

    public void save(User user){
        UserRole newUserRole = roleRepository.getByRole(Role.Student);
        user.setUserRole(newUserRole);
        UnconfirmedUser unUser = new UnconfirmedUser(user);
        unconfirmedUserRepository.save(unUser);
    }

    public void update(User user){
        Optional<User> byId = userRepository.findById(user.getId());
        if(byId.isPresent()){
            User userPresent = byId.get();
            if(!userPresent.equals(user)){
                userRepository.save(user);
            }
        }
    }

    public void confirm(User user){
        UnconfirmedUser byUser = unconfirmedUserRepository.getByUser(user);
        userRepository.save(byUser.getUser());
        unconfirmedUserRepository.delete(byUser);
    }

    public void setRole(long id, Role role){
        User byId = userRepository.getById(id);
        UserRole byRole = roleRepository.getByRole(role);
        byId.setUserRole(byRole);
        userRepository.save(byId);
    }

    public List<User> getUnconfirmedUsers(){
        return unconfirmedUserRepository.getAll();
    }
    public List<User> getConfirmedUsers(){
        return userRepository.getAllConfirmedUsers();
    }

    @Transactional
    public void deleteById(long id){
        if(unconfirmedUserRepository.existsByUser_Id(id)){
            unconfirmedUserRepository.deleteByUserId(id);
        }
        if (userRepository.existsById(id)){
            User byId = userRepository.getById(id);
            if(byId.getRole()==Role.Student){
                markRepository.deleteAllByUser(byId);
                ArrayList<Lesson> lessons = lessonRepository.findAllByStudentsContains(byId);
                for (Lesson lesson : lessons) {
                    lesson.getStudents().remove(byId);
                    lessonRepository.save(lesson);
                }
                userRepository.deleteById(id);
            } else {
                byId.setName("Deleted");
                byId.setUsername("Deleted");
                byId.setEmail("Forbidden");
                byId.setPassword("");
                userRepository.save(byId);
            }

        }

    }

    public List<User> getAll(Role role){
        return getConfirmedUsers().stream().filter(user -> user.getRole() == role).collect(Collectors.toList());
    }

}
