package com.v1.attendance.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.v1.attendance.configs.WebConfig;
import com.v1.attendance.models.entities.Lesson;
import com.v1.attendance.models.entities.LocalAddress;
import com.v1.attendance.models.entities.QRCode;
import com.v1.attendance.models.requests.GeneratorQRCodeRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@ConfigurationProperties(prefix = "spring")
@Slf4j
@Data
public class QRCodeServiceImpl implements QRCodeService{

    public static double teacherLatitude;
    public static double teacherLongitude;

    private String url;
    private ModelMapper modelMapper = new ModelMapper();
    private final JwtTokenProvider jwtTokenProvider;

    public QRCodeServiceImpl(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String generatedQRCode(GeneratorQRCodeRequest request, int width, int height) {
        //Get infor Lession to create new Qr
        Lesson lesson = WebConfig.lessonList.get(1);

        //Build a Qrcode to display
        QRCode qrCode = QRCode.builder()
                .id(lesson.getId())
                .subjectCode(lesson.getSubjectCode())
                .groupCode(lesson.getGroupCode())
                .semester(lesson.getSemester())
                .amphitheater(lesson.getAmphitheater())
                .localAddress(modelMapper.map(request, LocalAddress.class))
                .build();

        StringBuilder result = new StringBuilder();

        if(qrCode != null){

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            try {
                QRCodeWriter writer = new QRCodeWriter();

                String dataBuilder = url +
                        "/" + qrCode.getId() +
                        "/" + jwtTokenProvider.generateToken(qrCode);

                //Create object BitMatrix to stored Url
                BitMatrix bitMatrix = writer.encode(dataBuilder, BarcodeFormat.QR_CODE, width, height);

                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

                //declares to the browser this the image is base64 encoded;
                result.append("data: image/png;base64,");
                result.append(new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray())));

            } catch (WriterException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        teacherLatitude = Double.parseDouble(request.getLatitude());
        teacherLongitude = Double.parseDouble(request.getLongitude());
        System.out.println(teacherLatitude);
        return result.toString();
    }
}

