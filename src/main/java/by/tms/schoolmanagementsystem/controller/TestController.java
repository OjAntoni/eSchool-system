package by.tms.schoolmanagementsystem.controller;

import by.tms.schoolmanagementsystem.entity.lesson.Plan;
import by.tms.schoolmanagementsystem.entity.lesson.TimeBlock;
import by.tms.schoolmanagementsystem.entity.lesson.TimeTerm;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.repository.HomeworkRepository;
import by.tms.schoolmanagementsystem.repository.PlanRepository;
import by.tms.schoolmanagementsystem.repository.RoleRepository;
import by.tms.schoolmanagementsystem.repository.TimeTermRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/test")
public class TestController {
    RoleRepository repository;
    TimeTermRepository timeTermRepository;
    PlanRepository planRepository;
    HomeworkRepository homeworkRepository;

    @GetMapping("/roles")
    public void test(){
        UserRole userRole1 = new UserRole(Role.Admin);
        UserRole userRole2 = new UserRole(Role.Teacher);
        UserRole userRole3 = new UserRole(Role.Student);
        repository.saveAll(List.of(userRole1, userRole2, userRole3));
    }

    @GetMapping("/timeBlocks")
    public void test2(){
        TimeTerm timeTerm1 = new TimeTerm(TimeBlock.FIRST_TERM);
        TimeTerm timeTerm2 = new TimeTerm(TimeBlock.SECOND_TERM);
        TimeTerm timeTerm3 = new TimeTerm(TimeBlock.THIRD_TERM);
        TimeTerm timeTerm4 = new TimeTerm(TimeBlock.FOURTH_TERM);
        timeTermRepository.save(timeTerm1);
        timeTermRepository.save(timeTerm2);
        timeTermRepository.save(timeTerm3);
        timeTermRepository.save(timeTerm4);
    }

    @GetMapping("/plans")
    public void plans(){
        List<Plan> toSave = new ArrayList<>();
        Plan plan1 = new Plan(DayOfWeek.MONDAY, timeTermRepository.getByTimeBlock(TimeBlock.FIRST_TERM));
        toSave.add(plan1);
        Plan plan2 = new Plan(DayOfWeek.MONDAY, timeTermRepository.getByTimeBlock(TimeBlock.SECOND_TERM));
        toSave.add(plan2);
        Plan plan3 = new Plan(DayOfWeek.MONDAY, timeTermRepository.getByTimeBlock(TimeBlock.THIRD_TERM));
        toSave.add(plan3);
        Plan plan4 = new Plan(DayOfWeek.MONDAY, timeTermRepository.getByTimeBlock(TimeBlock.FOURTH_TERM));
        toSave.add(plan4);


        toSave.add(new Plan(DayOfWeek.TUESDAY, timeTermRepository.getByTimeBlock(TimeBlock.FIRST_TERM)));

        toSave.add(new Plan(DayOfWeek.TUESDAY, timeTermRepository.getByTimeBlock(TimeBlock.SECOND_TERM)));

        toSave.add(new Plan(DayOfWeek.TUESDAY, timeTermRepository.getByTimeBlock(TimeBlock.THIRD_TERM)));

        toSave.add(new Plan(DayOfWeek.TUESDAY, timeTermRepository.getByTimeBlock(TimeBlock.FOURTH_TERM)));


        toSave.add(new Plan(DayOfWeek.WEDNESDAY, timeTermRepository.getByTimeBlock(TimeBlock.FIRST_TERM)));

        toSave.add(new Plan(DayOfWeek.WEDNESDAY, timeTermRepository.getByTimeBlock(TimeBlock.SECOND_TERM)));

        toSave.add(new Plan(DayOfWeek.WEDNESDAY, timeTermRepository.getByTimeBlock(TimeBlock.THIRD_TERM)));

        toSave.add(new Plan(DayOfWeek.WEDNESDAY, timeTermRepository.getByTimeBlock(TimeBlock.FOURTH_TERM)));


        toSave.add(new Plan(DayOfWeek.THURSDAY, timeTermRepository.getByTimeBlock(TimeBlock.FIRST_TERM)));

        toSave.add(new Plan(DayOfWeek.THURSDAY, timeTermRepository.getByTimeBlock(TimeBlock.SECOND_TERM)));

        toSave.add(new Plan(DayOfWeek.THURSDAY, timeTermRepository.getByTimeBlock(TimeBlock.THIRD_TERM)));

        toSave.add(new Plan(DayOfWeek.THURSDAY, timeTermRepository.getByTimeBlock(TimeBlock.FOURTH_TERM)));


        toSave.add(new Plan(DayOfWeek.FRIDAY, timeTermRepository.getByTimeBlock(TimeBlock.FIRST_TERM)));

        toSave.add(new Plan(DayOfWeek.FRIDAY, timeTermRepository.getByTimeBlock(TimeBlock.SECOND_TERM)));

        toSave.add(new Plan(DayOfWeek.FRIDAY, timeTermRepository.getByTimeBlock(TimeBlock.THIRD_TERM)));

        toSave.add(new Plan(DayOfWeek.FRIDAY, timeTermRepository.getByTimeBlock(TimeBlock.FOURTH_TERM)));

        planRepository.saveAll(toSave);
    }

    @GetMapping("/getHm")
    public void getHm(){
        System.out.println(homeworkRepository.findById(1L));
    }
}
