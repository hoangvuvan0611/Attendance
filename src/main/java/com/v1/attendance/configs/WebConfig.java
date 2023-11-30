package com.v1.attendance.configs;

import com.v1.attendance.models.entities.Lesson;
import org.opencv.core.Core;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }

    public static List<Lesson> lessonList = new ArrayList<>();

    @Bean
    public void createMetaData(){

        Lesson lesson = Lesson.builder()
                .id(1L)
                .subjectCode("JAVA0012")
                .subjectName("Lập trình Java")
                .groupCode("11")
                .semester((byte) 1)
                .amphitheater("ND101")
                .build();

        Lesson lesson1 = Lesson.builder()
                .id(2L)
                .subjectCode("C0112")
                .subjectName("Lập trình C++")
                .groupCode("12")
                .semester((byte) 1)
                .amphitheater("ND401")
                .build();

        lessonList.add(lesson1);
        lessonList.add(lesson);
    }
}
