package by.tms.schoolmanagementsystem.controller;

import by.tms.schoolmanagementsystem.entity.announcement.Announcement;
import by.tms.schoolmanagementsystem.entity.announcement.AnnouncementDto;
import by.tms.schoolmanagementsystem.entity.email.EmailMessages;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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
    private SecurityService securityService;

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
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @GetMapping("/announcement/{id}")
    public ModelAndView getFullAnnouncementPage(@PathVariable long id, ModelAndView modelAndView){
        Optional<Announcement> byId = newsService.getById(id);
        if(byId.isEmpty()){
            modelAndView.setViewName("home");
            return modelAndView;
        }
        Announcement announcement = byId.get();
        modelAndView.addObject("announcement", announcement);
        modelAndView.setViewName("announcement");
        return modelAndView;
    }

    @GetMapping("/password/lost")
    public ModelAndView getPasswordPage(ModelAndView modelAndView){
        modelAndView.setViewName("password_forgotten");
        modelAndView.addObject("error", false);
        return modelAndView;
    }

    @PostMapping("/password/lost")
    public ModelAndView processAndSendCode(ModelAndView modelAndView, String username, HttpSession session){
        System.out.println("in password/lost post method");
        if(username==null || userService.findByUsername(username).isEmpty()){
            modelAndView.setViewName("password_forgotten");
            modelAndView.addObject("error", true);
        } else {
            User user = userService.findByUsername(username).get();
            String email = user.getEmail();
            String code = securityService.setCode(user);
            session.setAttribute("passwordUser", user);
            emailService.sendEmail(email, "Password code", EmailMessages.formPasswordCodeMessage(user.getName(),code));
            modelAndView.setViewName("password_code");
            modelAndView.addObject("invalidCode", false);
        }
        return modelAndView;
    }

    @GetMapping("/password/code")
    public ModelAndView getCodePage(ModelAndView modelAndView){
        System.out.println("/password/code отработало");
        modelAndView.setViewName("password_code");
        modelAndView.addObject("invalidCode", false);
        return modelAndView;
    }

    @PostMapping("/password/code")
    public ModelAndView processAccessCode(ModelAndView modelAndView, String code, HttpSession session, HttpServletResponse response){
        User user = (User) session.getAttribute("passwordUser");
        if (securityService.checkCode(user.getUsername(), code)) {
            securityService.deleteCode(user);
            String newPassword = securityService.setRandomPassword(user);
            emailService.sendEmail(user.getEmail(), "New password", EmailMessages.formNewPasswordMessage(user.getName(), newPassword));
            session.removeAttribute("userWaitingForCode");
            modelAndView.addObject("authUser", new User());
            modelAndView.addObject("isDataValid", true);
            modelAndView.setViewName("authorization");
        } else {
            modelAndView.setViewName("password_code");
            modelAndView.addObject("invalidCode", true);
        }
        return modelAndView;
    }
}
