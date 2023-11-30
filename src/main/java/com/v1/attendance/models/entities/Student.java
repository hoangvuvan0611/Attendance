package com.v1.attendance.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private Long id;
    private String fullName;
    private String classOfStudent;
    private Byte age;
    private Image img;
    private Date dateOfBirth;
    private String department;
    private String specialized;
}
