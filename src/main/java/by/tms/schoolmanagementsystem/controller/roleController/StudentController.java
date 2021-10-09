package by.tms.schoolmanagementsystem.controller.roleController;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.HomeworkService;
import by.tms.schoolmanagementsystem.service.LessonService;
import by.tms.schoolmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("student")
public class StudentController {
    private HomeworkService homeworkService;
    private UserService userService;
    private LessonService lessonService;

    @GetMapping("homework/all")
    public ModelAndView getAllHomeworkPage(ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Homework> homework = homeworkService.getSortedByPriority(user);
        modelAndView.addObject("homework", homework);
        modelAndView.setViewName("student_homework");
        return modelAndView;
    }
}
