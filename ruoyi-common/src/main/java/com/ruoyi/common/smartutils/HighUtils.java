package com.ruoyi.common.smartutils;

import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_videoio.*;
import org.bytedeco.javacv.Frame;

import static org.bytedeco.javacpp.opencv_core.absdiff;
import static org.bytedeco.javacpp.opencv_imgproc.*;


//def detect_loop(min_area):
//    print datetime.datetime.now()
//    camera = cv2.VideoCapture(0)
//    time.sleep(0.25)
//
//
//    # initialize the first frame in the video stream
//    firstFrame = None
//    print datetime.datetime.now()
//    # loop over the frames of the video
//    while True:
//        # grab the current frame and initialize the occupied/unoccupied
//        # text
//        (grabbed, frame) = camera.read()
//        text = "Unoccupied"
//
//        # if the frame could not be grabbed, then we have reached the end
//        # of the video
//        if not grabbed:
//            break
//
//        # resize the frame, convert it to grayscale, and blur it
//        frame = imutils.resize(frame, width=500)
//        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
//        gray = cv2.GaussianBlur(gray, (21, 21), 0)
//
//        # if the first frame is None, initialize it
//        if firstFrame is None:
//            firstFrame = gray
//            print datetime.datetime.now()
//            continue
//
//        # compute the absolute difference between the current frame and
//        # first frame
//        frameDelta = cv2.absdiff(firstFrame, gray)
//        thresh = cv2.threshold(frameDelta, 25, 255, cv2.THRESH_BINARY)[1]
//
//        # dilate the thresholded image to fill in holes, then find contours
//        # on thresholded image
//        thresh = cv2.dilate(thresh, None, iterations=2)
//        (cnts, _) = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL,
//            cv2.CHAIN_APPROX_SIMPLE)
//
//        # loop over the contours
//        for c in cnts:
//            # if the contour is too small, ignore it
//            if cv2.contourArea(c) < min_area:
//                print datetime.datetime.now(), "nc"
//                continue
//
//            # compute the bounding box for the contour, draw it on the frame,
//            # and update the text
//            (x, y, w, h) = cv2.boundingRect(c)
//            if x and y and w and h:
//                # 计算矩形的质心
//                x1 = x + w/2
//                y1 = y + h/2
//                print datetime.datetime.now(), x1, y1
//
//if __name__ == "__main__":
//    detect_loop(500)
//    # cleanup the camera and close any open windows
//    camera.release()
//    cv2.destroyAllWindows()
public class HighUtils {
    public void check(Boolean flag){
        VideoCapture capture=new VideoCapture(0);
        if(!capture.isOpened()){
            return;
        }
        Mat firstFrame=null;
        Mat currentFrame=null;
        while (flag){
            capture.read(currentFrame);

            Mat mat=new Mat();
            opencv_imgproc.resize(currentFrame,mat,new Size(500,500));
            cvtColor(mat, mat,COLOR_BGR2GRAY);
            //高斯滤波
            GaussianBlur(mat,mat,new Size (21, 21), 0);
            if(firstFrame==null){
                firstFrame=mat;
                continue;
            }
            //图像做差
            absdiff(firstFrame,mat,mat);
            //二值化
            threshold(mat,mat, 25, 255, THRESH_BINARY);
            //膨胀
            dilate(mat, mat, new Mat());
            MatVector matVector=new MatVector();
            findContours(mat.clone(),matVector, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);
            for (Mat item: matVector.get()) {
                if (contourArea(item) < 500){
                    System.out.println(contourArea(item));
                    continue;
                }
                Rect rect = boundingRect(item);
                System.out.println(666);
            }
        }
        capture.release();
    }
}
