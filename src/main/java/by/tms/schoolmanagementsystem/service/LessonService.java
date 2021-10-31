package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.lesson.Plan;
import by.tms.schoolmanagementsystem.entity.lesson.TimeTerm;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.repository.LessonRepository;
import by.tms.schoolmanagementsystem.repository.PlanRepository;
import by.tms.schoolmanagementsystem.repository.TimeTermRepository;
import by.tms.schoolmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class LessonService {
    private PlanRepository planRepository;
    private TimeTermRepository timeTermRepository;
    private LessonRepository lessonRepository;
    private UserRepository userRepository;

    @Transactional
    public void save(Lesson lesson){
        if(lesson!=null){
            Plan lessonPlan = lesson.getLessonPlan();
            TimeTerm byTimeBlock = timeTermRepository.getByTimeBlock(lessonPlan.getTimeTerm().getTimeBlock());
            Plan byDayAndTimeTerm = planRepository.getByDayAndTimeTerm(lessonPlan.getDay(), byTimeBlock);
            lesson.setLessonPlan(byDayAndTimeTerm);
            lessonRepository.save(lesson);
            System.out.println(lesson);
        }
    }

    @Transactional
    public List<Lesson> getAll(){
        return lessonRepository.findAll();
    }

    @Transactional
    public Lesson getById(long id){
        return lessonRepository.getById(id);
    }

    @Transactional
    public List<Lesson> getAllForTeacher(User user){
        if(user==null || user.getRole()!= Role.Teacher){
            return List.of();
        }
        return lessonRepository.findAllByTeacher(user);
    }

    @Transactional
    public List<Lesson> getAllForStudent(User student){
        if(student==null || !userRepository.existsById(student.getId()) || student.getRole()!=Role.Student){
            return List.of();
        } else {
            return lessonRepository.findAllByStudentsContains(student);
        }
    }

}
