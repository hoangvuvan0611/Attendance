package com.v1.attendance.controller;

import com.v1.attendance.models.requests.StudentAttendanceRequest;
import com.v1.attendance.services.AttendanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/atd")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check")
    public void checkStudentAttendance(@RequestBody StudentAttendanceRequest request){
        attendanceService.studentAttendance(request);

    }
}
