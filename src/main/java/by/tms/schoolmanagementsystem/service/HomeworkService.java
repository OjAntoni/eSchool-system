package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.mark.Mark;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.repository.HomeworkRepository;
import by.tms.schoolmanagementsystem.repository.LessonRepository;
import by.tms.schoolmanagementsystem.repository.MarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HomeworkService {
    private HomeworkRepository homeworkRepository;
    private LessonRepository lessonRepository;
    private MarkRepository markRepository;

    @Transactional(readOnly = true)
    public List<Homework> getAll(Lesson lesson){
        if(lesson==null){
            return new ArrayList<>();
        }
        return homeworkRepository.getAllByLesson(lesson);
    }

    @Transactional
    public void save(Homework homework){
        if(homework!=null) {
            Lesson lesson = homework.getLesson();
            List<User> students = lesson.getStudents();
            for (User student : students) {
                markRepository.save(new Mark(0, student, homework));
            }
            homeworkRepository.save(homework);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Homework> getById(long id){
        return homeworkRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Homework> getAllForTeacher(User teacher){
        if(teacher==null){
            return List.of();
        }
        return homeworkRepository.getAllByLesson_Teacher(teacher);
    }

    @Transactional(readOnly = true)
    public List<Homework> getAllForStudent(User student){
        if(student == null){
            return List.of();
        }
        ArrayList<Lesson> lessons = lessonRepository.findAllByStudentsContains(student);
        List<Homework> homework = new ArrayList<>();
        for (Lesson lesson : lessons) {
            ArrayList<Homework> homeworkTemp = homeworkRepository.getAllByLesson(lesson);
            homework.addAll(homeworkTemp);
        }
        homework.sort(Homework.getDeadlineTimeComparator());
        return homework;
    }

    @Transactional(readOnly = true)
    public List<Mark> getForHomework(Homework homework){
        if(homework == null){
            return List.of();
        }
        return markRepository.getAllByHomework(homework);
    }

    @Transactional
    public void save(Mark mark){
        if(mark!=null){
            if(markRepository.existsByIdAndValue(mark.getId(), mark.getValue())){
                mark.setDateTime(LocalDateTime.now());
                System.out.println(mark);
            }
            markRepository.save(mark);
        }
    }

    @Transactional
    public void updateMark(long id, int newValue){
        Optional<Mark> byId = markRepository.findById(id);
        if (byId.isPresent()){
            Mark mark = byId.get();
            if(mark.getValue() != newValue){
                mark.setDateTime(LocalDateTime.now());
            }
            mark.setValue(newValue);
            markRepository.save(mark);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Mark> getMarkById(long id){
        return markRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Homework> getSortedByPriority(Lesson lesson){
        return homeworkRepository.getAllByLessonAndDeadlineIsAfterOrderByDeadlineAsc(lesson, LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<Homework> getSortedByPriority(User student){
        ArrayList<Lesson> lessons = lessonRepository.findAllByStudentsContains(student);
        Set<Homework> homework = new TreeSet<>((o1, o2) -> {
            if(o1.getDeadline().isAfter(o2.getDeadline())){
                return 1;
            } else if(o1.getDeadline().isBefore(o2.getDeadline())){
                return -1;
            } else {
                return 0;
            }
        });
        for (Lesson lesson : lessons) {
            ArrayList<Homework> h = homeworkRepository.getAllByLesson(lesson);
            homework.addAll(h);
        }
        return new ArrayList<>(homework);
    }

    public List<Mark> getAllMarksForStudent(User student){
        ArrayList<Lesson> allByStudentsContains = lessonRepository.findAllByStudentsContains(student);
        List<Mark> studentMarks = new ArrayList<>();
        for (Lesson it : allByStudentsContains) {
            List<Mark> marksTmp = getForLesson(it).stream().filter(mark -> mark.getUser().getId()==student.getId()).collect(Collectors.toList());
            studentMarks.addAll(marksTmp);
        }
        return studentMarks;
    }

    public List<Mark> getAllMarksForTeacher(User teacher){
        ArrayList<Lesson> allByStudentsContains = lessonRepository.findAllByTeacher(teacher);
        List<Mark> teacherMarks = new ArrayList<>();
        for (Lesson it : allByStudentsContains) {
            teacherMarks.addAll(getForLesson(it));
        }
        return teacherMarks;
    }

    private List<Mark> getForLesson(Lesson lesson){
        ArrayList<Homework> allByLesson = homeworkRepository.getAllByLesson(lesson);
        List<Mark> marks = new ArrayList<>();
        for (Homework homework : allByLesson) {
            ArrayList<Mark> allByHomework = markRepository.getAllByHomework(homework);
            marks.addAll(allByHomework);
        }
        return marks;
    }
}
