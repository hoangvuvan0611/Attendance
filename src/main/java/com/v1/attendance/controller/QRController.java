package com.v1.attendance.controller;

import com.v1.attendance.models.entities.LocalAddress;
import com.v1.attendance.models.requests.GeneratorQRCodeRequest;
import com.v1.attendance.services.LessonServiceImpl;
import com.v1.attendance.services.QRCodeService;
import com.v1.attendance.services.QRCodeServiceImpl;
import com.v1.attendance.utils.LocatorAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/qr")
@Slf4j
public class QRController {
    
    private final QRCodeService qrCodeService;

    public QRController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping ("/get")
    public String getQRCode(@RequestBody GeneratorQRCodeRequest request){
       return qrCodeService.generatedQRCode(request, 500, 500);
    }

    @PostMapping("/distance")
    public double distance(@RequestBody GeneratorQRCodeRequest request){
        double latitude = Double.parseDouble(request.getLatitude());
        double longitude = Double.parseDouble(request.getLongitude());
        LocalAddress student = LocalAddress.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        LocalAddress teacher = LocalAddress.builder()
                .latitude(QRCodeServiceImpl.teacherLatitude)
                .longitude(QRCodeServiceImpl.teacherLongitude)
                .build();

        LocatorAddress distance = new LocatorAddress();
        return distance.distanceBetweenPoints(student, teacher);
    }
}
