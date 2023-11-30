package com.v1.attendance.utils.websocket;

import lombok.NoArgsConstructor;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;



@NoArgsConstructor
public class FaceDetection implements Runnable{

    private static final String CASCADE_FILE = "src/main/resources/static/haarcascades/haarcascade_frontalface_alt2.xml";

    @Override
    public void run() {}

    public byte[] detection(ByteArrayOutputStream byteArrayOutputStream){

        if(byteArrayOutputStream.toByteArray().length == 0)
            return null;

        MatOfByte matOfByte = new MatOfByte(byteArrayOutputStream.toByteArray());

        Mat srcToDetection = Imgcodecs.imdecode(matOfByte, Imgcodecs.IMREAD_UNCHANGED);

        CascadeClassifier cascadeClassifier = new CascadeClassifier(CASCADE_FILE);
        MatOfRect matOfRect = new MatOfRect();
        cascadeClassifier.detectMultiScale(srcToDetection, matOfRect);

        //Drawing boxes
        for(Rect rect : matOfRect.toArray()){
            Imgproc.rectangle(
                    srcToDetection,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0),
                    1
            );
        }

        MatOfByte matOfByteToReturn = new MatOfByte();
        Imgcodecs.imencode(".jpg", srcToDetection, matOfByteToReturn);

        return matOfByteToReturn.toArray();
    }
}
