package com.ruoyi.common.smartutils;

import java.util.Arrays;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class FaceUtils {
    @Autowired
    private InitCV initCV;

    /*public static double compareFace(String img1, String img2) throws IOException {
        Mat mat1 = MatUtils.base642Mat(img1);
        Mat mat2 = MatUtils.base642Mat(img2);
        return compareImage(mat1,mat2);
    }

    public static double compareImage(Mat mat_1, Mat mat_2) {
        Mat mat1 = convMat(mat_1);
        Mat mat2 = convMat(mat_2);
        Mat hist1 = new Mat();
        Mat hist2 = new Mat();
        //颜色范围
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        //直方图大小， 越大匹配越精确 (越慢)
        MatOfInt histSize = new MatOfInt(1000);

        Imgproc.calcHist(Arrays.asList(mat1), new MatOfInt(0), new Mat(), hist1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat2), new MatOfInt(0), new Mat(), hist2, histSize, ranges);

        // CORREL 相关系数
        double res = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);
        return res;
    }


    // 灰度化人脸
    public static Mat convMat(Mat image0) {
        //Mat image0 = Imgcodecs.imread(img);

        Mat image1 = new Mat();
        // 灰度化
        Imgproc.cvtColor(image0, image1, Imgproc.COLOR_BGR2GRAY);
        // 探测人脸
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image1, faceDetections);
        // rect中人脸图片的范围
        for (Rect rect : faceDetections.toArray()) {
            Mat face = new Mat(image1, rect);
            return face;
        }
        return null;
    }


    public static void main(String[] args) {
        try {
            double v = compareImage(MatUtils.imagePath2Mat("E:\\111.jpg"), MatUtils.imagePath2Mat("E:\\111.jpg"));
            System.out.println(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    // 灰度化人脸
    public  Mat conv_Mat(Mat image0) {
        //Mat image0 = Imgcodecs.imread(img);

        Mat image1 = new Mat();
        // 灰度化
        Imgproc.cvtColor(image0, image1, Imgproc.COLOR_BGR2GRAY);
        // 探测人脸
        MatOfRect faceDetections = new MatOfRect();
        //initCV.faceDetector.detectMultiScale(image1, faceDetections);
        // rect中人脸图片的范围
        for (Rect rect : faceDetections.toArray()) {
            Mat face = new Mat(image1, rect);
            return face;
        }
        return null;
    }

    public  double compare_image(String img_1, String img_2) {
        Mat mat_1 = conv_Mat(Imgcodecs.imread(img_1));
        Mat mat_2 = conv_Mat(Imgcodecs.imread(img_2));
        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        //颜色范围
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        //直方图大小， 越大匹配越精确 (越慢)
        MatOfInt histSize = new MatOfInt(1000);

        Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);

        // CORREL 相关系数
        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        return res;
    }

    /*public static void main(String[] args) {
        *//*String basePicPath = "E:/";
        double compareHist = compare_image(basePicPath + "111.jpg", basePicPath + "111.jpg");
        System.out.println(compareHist);
        if (compareHist > 0.72) {
            System.out.println("人脸匹配");
        } else {
            System.out.println("人脸不匹配");
        }*//*
        Mat frame= null;
        try {
            frame = MatUtils.imagePath2Mat("E:\\111.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(frame, faceDetections);
        Rect[] rectArray = faceDetections.toArray();
        if (rectArray.length > 0) {
            System.out.println(rectArray.length);
        }
    }*/


}
