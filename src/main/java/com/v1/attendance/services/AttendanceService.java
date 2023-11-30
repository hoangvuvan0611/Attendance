package com.v1.attendance.services;

import com.v1.attendance.models.requests.StudentAttendanceRequest;
import org.springframework.stereotype.Service;

public interface AttendanceService {
    public boolean studentAttendance(StudentAttendanceRequest request);
}
