package com.v1.attendance.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonResponseAttendance {
    private String subjectCode;
    private String subjectName;
    private String groupCode;
    private Byte semester;
    private Date startTime;
}
