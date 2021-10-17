package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.repository.HomeworkRepository;
import by.tms.schoolmanagementsystem.repository.LessonRepository;
import by.tms.schoolmanagementsystem.repository.MarkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HomeworkServiceTest {
    @Mock
    private HomeworkRepository homeworkRepository;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private MarkRepository markRepository;

    @Test
    void getAll() {
        //todo
    }

    @Test
    void getAllForTeacher() {
        //todo
    }

    @Test
    void getAllForStudent() {
        //todo
    }

    @Test
    void getForHomework() {
        //todo
    }
}