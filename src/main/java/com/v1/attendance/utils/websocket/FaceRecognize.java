package com.v1.attendance.utils.websocket;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;

import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class FaceRecognize implements Runnable{

    private final CascadeClassifier cascade = new CascadeClassifier("src/main/resources/static/haarcascades/haarcascade_frontalface_alt2.xml");
    Mat srcImg = new Mat();
    RectVector rectVectorDetectedFace = new RectVector();
    LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();

    public FaceRecognize() {
        recognizer.read("src/main/resources/static/training/classifierLBPH.yml");
        recognizer.setThreshold(70);
    }

    @Override
    public void run() {

    }

    public String recognize(ByteArrayOutputStream byteArrayStream){

        if(byteArrayStream.toByteArray().length == 0)
            return null;

        BytePointer bytePointer = new BytePointer(byteArrayStream.toByteArray());
        srcImg = imdecode(new Mat(bytePointer), IMREAD_ANYDEPTH|IMREAD_ANYCOLOR);

        if(srcImg == null)
            return null;

        //imageGray to recognized
        Mat imgGray = new Mat();
        cvtColor(srcImg, imgGray, COLOR_BGR2GRAY);

        cascade.detectMultiScale(imgGray, rectVectorDetectedFace);

        int prediction = 0;
        double c = 0;
        for(Rect rect: rectVectorDetectedFace.get()){

            rectangle(srcImg, rect, new Scalar(0, 255, 0, 3), 1,0,0);

            Mat faceToRecognized = new Mat(imgGray, rect);
            resize(faceToRecognized, faceToRecognized, new Size(160, 160));

            IntPointer label = new IntPointer(1);
            DoublePointer confidence = new DoublePointer(1);
            recognizer.predict(faceToRecognized, label, confidence);

            prediction = label.get(0);
            c = confidence.get(0);
            if(prediction == -1){
                rectangle(srcImg, rect, new Scalar(255, 0, 0, 3), 1,0,0);
            }else{
                rectangle(srcImg, rect, new Scalar(0, 255, 0, 3), 1,0,0);

            }
        }

//        imencode(".jpg", srcImg, bytePointer);
//        return bytePointer.getStringBytes();
        switch (prediction) {
            case 1 -> {
                return("Tôi - " + c);
            }
            case 2 -> {
                return ("Vũ Văn Hoàng - " + c);
            }
            case 3 -> {
                return ("Hoàng bách - " + c);
            }
            case 4 -> {
                return ("Anh đức - " + c);
            }
            default -> {
                return null;
            }
        }
    }

    public void trainingLBPH(){

        File[] files = readDirectory("src/main/resources/static/training");
        assert files != null;
        MatVector images = new MatVector(files.length);

        Mat label = new Mat(files.length, 1, CV_32SC1);
        IntBuffer labelsBuffer = label.createBuffer();

        int counter = 0;
        for(File img: files){
            Mat matImg = imread(img.getAbsolutePath(), COLOR_BGRA2GRAY);
            int personId = Integer.parseInt(img.getName().split("_")[0]);

            resize(matImg, matImg, new Size(160, 160));
            images.put(counter, matImg);
            labelsBuffer.put(counter, personId);
            counter++;
        }
        recognizer.train(images, label);
        recognizer.save("src/main/resources/static/training/classifierLBPH.yml");
    }

    public static void main(String[] args) {
        FaceRecognize faceRecognize = new FaceRecognize();
        faceRecognize.trainingLBPH();
    }

    public void registerFaces(){
        File[] files = readDirectory("src/main/resources/static/metadata");

        assert files != null;
        for(File img: files) {
            Mat matImg = imread(img.getAbsolutePath());

            RectVector rectVector = new RectVector();
            cascade.detectMultiScale(matImg, rectVector);

            for (Rect rect : rectVector.get()) {
                rectangle(matImg, rect, new Scalar(255, 255, 0, 3), 1, 0, 0);

                Mat facePerson = new Mat(matImg, rect);
                resize(facePerson, facePerson, new Size(160, 160));
                cvtColor(facePerson, facePerson, COLOR_BGRA2GRAY);
                imwrite("src/main/resources/static/training/" + img.getName(),facePerson);
            }
        }
    }

    public File[] readDirectory(String path){

        //mention the directory the faces has been saved
        File directory = new File(path);

        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg")
                        || name.endsWith(".pgm")
                        || name.endsWith(".png");
            }
        };
        return directory.listFiles(filenameFilter);
    }
}
