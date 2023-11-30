package com.v1.attendance.services;

import com.v1.attendance.models.requests.StudentAttendanceRequest;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService{

    @Override
    public boolean studentAttendance(StudentAttendanceRequest request) {
        return false;
    }
}
