package com.v1.attendance.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QRCode {
    private Long id;
    private String subjectCode;
    private String groupCode;
    private Byte semester;
    private String amphitheater;
    private Timestamp dateTime;
    private LocalAddress localAddress;
}
