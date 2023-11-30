package com.v1.attendance.controller;

import com.v1.attendance.models.requests.LessonAttendanceRequest;
import com.v1.attendance.models.responses.LessonResponseAttendance;
import com.v1.attendance.services.LessonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/data_attendance")
    public LessonResponseAttendance getLesson(@RequestBody LessonAttendanceRequest requestAttendance){
        return lessonService.getLesson(requestAttendance);
    }
}
