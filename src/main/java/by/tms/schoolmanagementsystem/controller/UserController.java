package by.tms.schoolmanagementsystem.controller;

import by.tms.schoolmanagementsystem.entity.announcement.Announcement;
import by.tms.schoolmanagementsystem.entity.announcement.AnnouncementDto;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.EmailService;
import by.tms.schoolmanagementsystem.service.NewsService;
import by.tms.schoolmanagementsystem.service.RoleService;
import by.tms.schoolmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private EmailService emailService;
    private NewsService newsService;
    private RoleService roleService;

    @GetMapping("/reg")
    public ModelAndView getUserRegPage(ModelAndView modelAndView){
        modelAndView.addObject("newUser", new User());
        modelAndView.addObject("userExists", false);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/reg")
    public ModelAndView processUserRegistration(@Valid @ModelAttribute(name = "newUser") User newUser, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("registration");
        } else {
            if(userService.existsByUsername(newUser.getUsername())){
                modelAndView.addObject("userExists", true);
                modelAndView.setViewName("registration");
            } else {
                userService.save(newUser);
                //emailService.sendEmail(newUser.getEmail(), "Registration", "Hello in our service\n"+newUser);
                modelAndView.addObject("authUser", new User());
                modelAndView.setViewName("authorization");
            }
        }
        return modelAndView;
    }

    @GetMapping("/auth")
    public ModelAndView getUserAuthPage(ModelAndView modelAndView){
        modelAndView.addObject("authUser", new User());
        modelAndView.addObject("isDataValid", true);
        modelAndView.setViewName("authorization");
        return modelAndView;
    }

    @PostMapping("/auth")
    public ModelAndView processUserAuthorization(@ModelAttribute(name = "authUser") User authUser, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> byUsername = userService.findByUsername(authUser.getUsername());
        if (byUsername.isEmpty()){
            modelAndView.addObject("isDataValid", false);
            modelAndView.setViewName("authorization");
            return modelAndView;
        } else {
            User user = byUsername.get();
            if(user.getPassword().equals(authUser.getPassword())){
                session.setAttribute("user", user);
                modelAndView.setViewName("redirect:/home");
                return modelAndView;
            }
        }
        modelAndView.addObject("isDataValid", false);
        modelAndView.setViewName("authorization");
        return modelAndView;
    }

    @GetMapping("/announcement/new")
    public ModelAndView getAnnouncementPage(ModelAndView modelAndView){
        modelAndView.addObject("announcementDto", new AnnouncementDto());
        modelAndView.setViewName("new_announcement");
        return modelAndView;
    }

    @PostMapping("/announcement/new")
    public ModelAndView createAnnouncement(@Valid @ModelAttribute AnnouncementDto announcementDto, BindingResult bindingResult, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("new_announcement");
            return modelAndView;
        }
        User author = (User) session.getAttribute("user");
        Role role = announcementDto.role;
        String title = announcementDto.title;
        String text = announcementDto.text;
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setAuthor(author);
        UserRole userRole = roleService.getUserRole(role);
        newAnnouncement.setDestinationRole(userRole);
        newAnnouncement.setTitle(title);
        newAnnouncement.setText(text);
        newsService.save(newAnnouncement);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
