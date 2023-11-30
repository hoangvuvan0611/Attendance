package com.v1.attendance.services;

import com.v1.attendance.configs.WebConfig;
import com.v1.attendance.models.entities.Lesson;
import com.v1.attendance.models.entities.LocalAddress;
import com.v1.attendance.models.requests.LessonAttendanceRequest;
import com.v1.attendance.models.responses.LessonResponseAttendance;
import com.v1.attendance.utils.LocatorAddress;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService{
    @Override
    public LessonResponseAttendance getLesson(LessonAttendanceRequest requestAttendance) {

        Lesson lesson = new Lesson();
        for(int i=0; i<WebConfig.lessonList.size(); i++){
            if(WebConfig.lessonList.get(i).getId().equals(Long.parseLong(requestAttendance.getId()))){
                lesson = WebConfig.lessonList.get(i);
            }
        }

//        double latitude1 = Double.parseDouble(requestAttendance.getLatitude());
//        double longitude1 = Double.parseDouble(requestAttendance.getLongitude());
//        LocalAddress student = LocalAddress.builder()
//                .latitude(latitude1)
//                .longitude(longitude1)
//                .build();
//
//        double latitude2 = QRCodeServiceImpl.teacherLatitude;
//        double longitude2 = QRCodeServiceImpl.teacherLongitude;
//        LocalAddress teacher = LocalAddress.builder()
//                .latitude(latitude2)
//                .longitude(longitude2)
//                .build();
//
//        LocatorAddress locatorAddress = new LocatorAddress();
//        double distance = locatorAddress.distanceBetweenPoints(student, teacher);

        return LessonResponseAttendance.builder()
                .subjectCode(lesson.getSubjectCode())
                .groupCode(lesson.getGroupCode())
                .subjectName(lesson.getSubjectName())
                .semester(lesson.getSemester())
                .groupCode(lesson.getGroupCode())
                .build();
    }
}
