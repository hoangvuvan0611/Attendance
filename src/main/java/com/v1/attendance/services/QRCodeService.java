package com.v1.attendance.services;

import com.v1.attendance.models.entities.QRCode;
import com.v1.attendance.models.requests.GeneratorQRCodeRequest;


public interface QRCodeService {
    public String generatedQRCode(GeneratorQRCodeRequest request, int width, int height);
}
