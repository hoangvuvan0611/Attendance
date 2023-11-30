package com.v1.attendance.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {
    private Long id;
    private String subjectCode;
    private String subjectName;
    private String groupCode;
    private Byte semester;
    private Date startTime;
    private String amphitheater;
}
