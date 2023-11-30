package com.v1.attendance.services;

import com.v1.attendance.models.requests.LessonAttendanceRequest;
import com.v1.attendance.models.responses.LessonResponseAttendance;

public interface LessonService {
    public LessonResponseAttendance getLesson(LessonAttendanceRequest requestAttendance);
}
