package com.ruoyi.common.smartutils;

import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_imgproc;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

@Component
public class FaceFeatureUtil {
    @Autowired
    private InitCV initCV;
    /**
     * 把人像图片的人脸识别并裁剪出来，保存到同目录
     * @throws Exception
     * @throws InterruptedException
     */
    public  List<Mat> detectFace(Mat image)
    {
        //Mat image = imread(path);
        if (image.empty()) {
            throw new RuntimeException("加载图片出错，请检查图片路径！");

        }
//        imshow("显示原始图像", image);

        RectVector faces = new RectVector();


        //当前帧图片进行灰度+直方均衡
       Mat videoMatGray = new Mat();
        opencv_imgproc.cvtColor(image, videoMatGray, Imgproc.COLOR_BGRA2GRAY);
        opencv_imgproc.equalizeHist(videoMatGray, videoMatGray);

        //使用检测器进行检测，把结果放进集合中
        initCV.faceDetector.detectMultiScale(videoMatGray, faces);

        //把所有人脸数据绘制到图片中
       /* File imagePath = new File(path);
        String dir = imagePath.getParent();
        String name = imagePath.getName().substring(0, imagePath.getName().lastIndexOf("."));*/
        List<Mat> matList=new ArrayList<>();
        for (int i = 0; i < faces.size(); i++) {
            Rect face = faces.get(i);
            Mat img_region = new Mat(image, face);
            matList.add(img_region);
//            imshow("人脸裁剪"+i, img_region);
            //imwrite(dir+File.separator+name+" face"+i+".png", img_region);


        }

        return matList;
//        waitKey(0);
    }
    //import cv2 as cv
    //import time
    //
    //
    //# 检测人脸并绘制人脸bounding box
    //def getFaceBox(net, frame, conf_threshold=0.7):
    //    frameOpencvDnn = frame.copy()
    //    frameHeight = frameOpencvDnn.shape[0]  # 高就是矩阵有多少行
    //    frameWidth = frameOpencvDnn.shape[1]  # 宽就是矩阵有多少列
    //    blob = cv.dnn.blobFromImage(frameOpencvDnn, 1.0, (300, 300), [104, 117, 123], True, False)
    //    #  blobFromImage(image[, scalefactor[, size[, mean[, swapRB[, crop[, ddepth]]]]]]) -> retval  返回值   # swapRB是交换第一个和最后一个通道   返回按NCHW尺寸顺序排列的4 Mat值
    //    net.setInput(blob)
    //    detections = net.forward()  # 网络进行前向传播，检测人脸
    //    bboxes = []
    //    for i in range(detections.shape[2]):
    //        confidence = detections[0, 0, i, 2]
    //        if confidence > conf_threshold:
    //            x1 = int(detections[0, 0, i, 3] * frameWidth)
    //            y1 = int(detections[0, 0, i, 4] * frameHeight)
    //            x2 = int(detections[0, 0, i, 5] * frameWidth)
    //            y2 = int(detections[0, 0, i, 6] * frameHeight)
    //            bboxes.append([x1, y1, x2, y2])  # bounding box 的坐标
    //            cv.rectangle(frameOpencvDnn, (x1, y1), (x2, y2), (0, 255, 0), int(round(frameHeight / 150)),
    //                         8)  # rectangle(img, pt1, pt2, color[, thickness[, lineType[, shift]]]) -> img
    //    return frameOpencvDnn, bboxes
    //
    //
    //# 网络模型  和  预训练模型
    //faceProto = "E:/workOpencv/opencv_tutorial/data/models/face_detector/opencv_face_detector.pbtxt"
    //faceModel = "E:/workOpencv/opencv_tutorial/data/models/face_detector/opencv_face_detector_uint8.pb"
    //
    //ageProto = "E:/workOpencv/opencv_tutorial/data/models/cnn_age_gender_models/age_deploy.prototxt"
    //ageModel = "E:/workOpencv/opencv_tutorial/data/models/cnn_age_gender_models/age_net.caffemodel"
    //
    //genderProto = "E:/workOpencv/opencv_tutorial/data/models/cnn_age_gender_models/gender_deploy.prototxt"
    //genderModel = "E:/workOpencv/opencv_tutorial/data/models/cnn_age_gender_models/gender_net.caffemodel"
    //
    //# 模型均值
    //MODEL_MEAN_VALUES = (78.4263377603, 87.7689143744, 114.895847746)
    //ageList = ['(0-2)', '(4-6)', '(8-12)', '(15-20)', '(25-32)', '(38-43)', '(48-53)', '(60-100)']
    //genderList = ['Male', 'Female']
    //
    //# 加载网络
    //ageNet = cv.dnn.readNet(ageModel, ageProto)
    //genderNet = cv.dnn.readNet(genderModel, genderProto)
    //# 人脸检测的网络和模型
    //faceNet = cv.dnn.readNet(faceModel, faceProto)
    //
    //# 打开一个视频文件或一张图片或一个摄像头
    //cap = cv.VideoCapture(0)
    //padding = 20
    //while cv.waitKey(1) < 0:
    //    # Read frame
    //    t = time.time()
    //    hasFrame, frame = cap.read()
    //    frame = cv.flip(frame, 1)
    //    if not hasFrame:
    //        cv.waitKey()
    //        break
    //
    //    frameFace, bboxes = getFaceBox(faceNet, frame)
    //    if not bboxes:
    //        print("No face Detected, Checking next frame")
    //        continue
    //
    //    for bbox in bboxes:
    //        # print(bbox)   # 取出box框住的脸部进行检测,返回的是脸部图片
    //        face = frame[max(0, bbox[1] - padding):min(bbox[3] + padding, frame.shape[0] - 1),
    //               max(0, bbox[0] - padding):min(bbox[2] + padding, frame.shape[1] - 1)]
    //        print("=======", type(face), face.shape)  #  <class 'numpy.ndarray'> (166, 154, 3)
    //        #
    //        blob = cv.dnn.blobFromImage(face, 1.0, (227, 227), MODEL_MEAN_VALUES, swapRB=False)
    //        print("======", type(blob), blob.shape)  # <class 'numpy.ndarray'> (1, 3, 227, 227)
    //        genderNet.setInput(blob)   # blob输入网络进行性别的检测
    //        genderPreds = genderNet.forward()   # 性别检测进行前向传播
    //        print("++++++", type(genderPreds), genderPreds.shape, genderPreds)   # <class 'numpy.ndarray'> (1, 2)  [[9.9999917e-01 8.6268375e-07]]  变化的值
    //        gender = genderList[genderPreds[0].argmax()]   # 分类  返回性别类型
    //        # print("Gender Output : {}".format(genderPreds))
    //        print("Gender : {}, conf = {:.3f}".format(gender, genderPreds[0].max()))
    //
    //        ageNet.setInput(blob)
    //        agePreds = ageNet.forward()
    //        age = ageList[agePreds[0].argmax()]
    //        print(agePreds[0].argmax())  # 3
    //        print("*********", agePreds[0])   #  [4.5557402e-07 1.9009208e-06 2.8783199e-04 9.9841607e-01 1.5261240e-04 1.0924522e-03 1.3928890e-05 3.4708322e-05]
    //        print("Age Output : {}".format(agePreds))
    //        print("Age : {}, conf = {:.3f}".format(age, agePreds[0].max()))
    //
    //        label = "{},{}".format(gender, age)
    //        cv.putText(frameFace, label, (bbox[0], bbox[1] - 10), cv.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 255), 2,
    //                   cv.LINE_AA)  # putText(img, text, org, fontFace, fontScale, color[, thickness[, lineType[, bottomLeftOrigin]]]) -> img
    //        cv.imshow("Age Gender Demo", frameFace)
    //    print("time : {:.3f} ms".format(time.time() - t))
}
