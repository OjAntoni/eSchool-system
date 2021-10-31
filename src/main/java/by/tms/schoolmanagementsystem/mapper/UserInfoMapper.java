package by.tms.schoolmanagementsystem.mapper;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.mark.Mark;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.entity.user.UserInfo;
import by.tms.schoolmanagementsystem.repository.LessonRepository;
import by.tms.schoolmanagementsystem.repository.UserRepository;
import by.tms.schoolmanagementsystem.service.HomeworkService;
import by.tms.schoolmanagementsystem.service.LessonService;
import by.tms.schoolmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserInfoMapper {
    private HomeworkService homeworkService;
    private LessonService lessonService;
    private UserService userService;

    public UserInfo getUserInfo(User user){
        if (userService.findById(user.getId()).isPresent()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUser(user);
            int lessonsNumber;
            List<Homework> homework;
            List<Mark> bestMarks;
            if(user.getRole()== Role.Student){
                lessonsNumber = lessonService.getAllForStudent(user).size();
                userInfo.setHomeworkCount(homeworkService.getAllForStudent(user).size());
                homework = homeworkService.getAllForStudent(user);
            } else {
                lessonsNumber = lessonService.getAllForTeacher(user).size();
                userInfo.setHomeworkCount(homeworkService.getAllForTeacher(user).size());
                homework = homeworkService.getAllForTeacher(user);
            }
            long homeworkPast = homework.stream().filter(h -> h.getDeadline().isBefore(LocalDateTime.now())).count();
            long homeworkFuture = homework.stream().filter(h -> h.getDeadline().isAfter(LocalDateTime.now())).count();
            userInfo.setHomeworkPast((int) homeworkPast);
            userInfo.setHomeworkFuture((int) homeworkFuture);
            userInfo.setLessonCount(lessonsNumber);
            userInfo.setBusiestDays(countBusiestDays(user));
            bestMarks = getBestMarks(user);
            userInfo.setBestMarks(bestMarks);
            //todo add loading these fields:
            //    List<Lesson> lessons;
            return userInfo;
        } else {
            throw new IllegalArgumentException("Not existing user provided.");
        }
    }

    private List<DayOfWeek> countBusiestDays(User user){
        List<Lesson> lessons;
        if(user.getRole()==Role.Student){
            lessons = lessonService.getAllForStudent(user);
        } else {
            lessons = lessonService.getAllForTeacher(user);
        }
        List<DayOfWeek> busiestDays = new ArrayList<>();
        int maxLessonsPerDay = -1;
        for(int i = 1; i <= 5; i++){
            int weekDayIndex = i;
            int lessonsForDay = (int) lessons.stream()
                    .filter(lesson -> lesson.getLessonPlan().getDay().getValue() == weekDayIndex)
                    .count();
            if(lessonsForDay >= maxLessonsPerDay){
                busiestDays.add(DayOfWeek.of(i));
                maxLessonsPerDay = lessonsForDay;
            }
        }
        return busiestDays;
    }

    private List<Mark> getBestMarks(User user){
        List<Mark> marks;
        if(user.getRole()==Role.Student){
            marks = homeworkService.getAllMarksForStudent(user);
            System.out.println(marks);
        } else {
            marks = homeworkService.getAllMarksForTeacher(user);
        }
        List<Mark> lastWeekMarks = marks.stream().filter(m -> m.getDateTime().isAfter(LocalDateTime.now().minusDays(7))).collect(Collectors.toList());
        lastWeekMarks = lastWeekMarks.stream().sorted(Comparator.comparingInt(Mark::getValue)).collect(Collectors.toList());
        Collections.reverse(lastWeekMarks);
        if(lastWeekMarks.size()==0){
            return lastWeekMarks;
        }
        List<Mark> tmp;
        if(lastWeekMarks.size()>=3){
            tmp = lastWeekMarks.subList(0, 3);
        } else {
            tmp = lastWeekMarks.subList(0, lastWeekMarks.size() - 1);
        }
        return tmp;
    }
}
