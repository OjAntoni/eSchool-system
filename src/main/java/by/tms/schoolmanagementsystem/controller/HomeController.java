package by.tms.schoolmanagementsystem.controller;

import by.tms.schoolmanagementsystem.entity.announcement.Announcement;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/home")
public class HomeController {
    private NewsService newsService;

    @GetMapping
    ModelAndView getHomePage(ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Announcement> news = newsService.getAll(user);
        modelAndView.addObject("news", news);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
