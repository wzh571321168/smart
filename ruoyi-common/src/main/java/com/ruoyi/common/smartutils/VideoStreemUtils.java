package com.ruoyi.common.smartutils;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoStreemUtils {
    /**
     * 按帧录制本机摄像头视频（边预览边录制，停止预览即停止录制）
     *
     * @author eguid
     * @param outputFile -录制的文件路径，也可以是rtsp或者rtmp等流媒体服务器发布地址
     * @param frameRate - 视频帧率
     * @throws Exception
     * @throws InterruptedException
     * @throws org.bytedeco.javacv.FrameRecorder.Exception
     */
   /* public static void recordCamera(String outputFile, double frameRate)
            throws Exception, InterruptedException, org.bytedeco.javacv.FrameRecorder.Exception {
        Loader.load(opencv_objdetect.class);
        FrameGrabber grabber = FrameGrabber.createDefault(1);//本机摄像头默认0，这里使用javacv的抓取器，至于使用的是ffmpeg还是opencv，请自行查看源码
        grabber.start();//开启抓取器

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();//转换器
        opencv_core.IplImage grabbedImage = converter.convert(grabber.grab());//抓取一帧视频并将其转换为图像，至于用这个图像用来做什么？加水印，人脸识别等等自行添加
        int width = grabbedImage.width();
        int height = grabbedImage.height();

        FrameRecorder recorder = FrameRecorder.createDefault(outputFile, width, height);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
        recorder.setFormat("flv");//封装格式，如果是推送到rtmp就必须是flv封装格式
        recorder.setFrameRate(frameRate);

        recorder.start();//开启录制器
        long startTime=0;
        long videoTS=0;
        CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        Frame rotatedFrame=converter.convert(grabbedImage);//不知道为什么这里不做转换就不能推到rtmp
        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
            rotatedFrame = converter.convert(grabbedImage);
            frame.showImage(rotatedFrame);
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            videoTS = 1000 * (System.currentTimeMillis() - startTime);
            recorder.setTimestamp(videoTS);
            recorder.record(rotatedFrame);
            Thread.sleep(40);
        }
        frame.dispose();
        recorder.stop();
        recorder.release();
        grabber.stop();

    }


    public static void main(String[] args) throws Exception, InterruptedException, org.bytedeco.javacv.FrameRecorder.Exception {
        recordCamera("output.mp4",25);
    }*/

    static boolean exit  = false;
    public static void main(String[] args) throws Exception {
        System.out.println("start...");
        String rtmpPath = "rtmp://127.0.0.1:1935/stream/monitor";
        String rtspPath = "rtsp://127.0.0.1:554/test";

        int audioRecord =0; // 0 = 不录制，1=录制
        boolean saveVideo = false;
        push(rtmpPath,rtspPath,audioRecord,saveVideo);

        System.out.println("end...");
    }

        public static void push(String rtmpPath,String rtspPath,int audioRecord,boolean saveVideo ) throws Exception  {
        // 使用rtsp的时候需要使用 FFmpegFrameGrabber，不能再用 FrameGrabber
        int width = 640,height = 480;
        OpenCVFrameGrabber grabber = OpenCVFrameGrabber .createDefault(rtspPath);
        grabber.setOption("rtsp_transport", "tcp"); // 使用tcp的方式，不然会丢包很严重

        grabber.setImageWidth(width);
        grabber.setImageHeight(height);
        System.out.println("grabber start");
        grabber.start();
        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(rtmpPath,width,height, audioRecord);
        recorder.setInterleaved(true);
        //recorder.setVideoOption("crf","28");
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
        recorder.setFormat("flv"); // rtmp的类型
        recorder.setFrameRate(25);
        recorder.setImageWidth(width);recorder.setImageHeight(height);
        recorder.setPixelFormat(0); // yuv420p
        System.out.println("recorder start");
        recorder.start();
        //
        //OpenCVFrameConverter.ToIplImage conveter = new OpenCVFrameConverter.ToIplImage();
        System.out.println("all start!!");
        int count = 0;
        Frame frame;
        while(!exit&&( frame = grabber.grab())!=null){
            count++;
            ;
            if(frame == null){
                continue;
            }
            if(count % 100 == 0){
                System.out.println("count="+count);
            }
            recorder.record(frame);
            Thread.sleep(20);
        }

        grabber.stop();
        grabber.release();
        recorder.stop();
        recorder.release();
    }
    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    static boolean flag=false;
   /* public static void main(String[] args)  {
        // Preload the opencv_objdetect module to work around a known bug.
        try {
            push();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    public static void push() throws FrameGrabber.Exception, FrameRecorder.Exception, InterruptedException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// 获取当前屏幕大小
        Rectangle rectangle = new Rectangle(screenSize);// 指定捕获屏幕区域大小，这里使用全屏捕获
        //做好自己!--eguid，eguid的博客是:blog.csdn.net/eguid_1
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//本地环境
        GraphicsDevice[] gs = ge.getScreenDevices();//获取本地屏幕设备列表
        System.err.println("温馨提示，找到"+gs.length+"个屏幕设备");
        Robot robot=null;
        int ret=-1;
        for(int index=0;index<10;index++){
            GraphicsDevice g=gs[index];
            try {
                robot= new Robot(g);
                BufferedImage img=robot.createScreenCapture(rectangle);
                if(img!=null&&img.getWidth()>1){
                    ret=index;
                    break;
                }
            } catch (AWTException e) {
                System.err.println("打开第"+index+"个屏幕设备失败，尝试打开第"+(index+1)+"个屏幕设备");
            }
        }
        System.err.println("打开的屏幕序号："+ret);
        String str = Loader.load(opencv_objdetect.class);
        System.out.println(str);
        int width = 400;
        int height = 300;
        //String outputFile = "d:\\record.mp4";
        String outputFile = "rtmp://127.0.0.1:1935/stream/test";
        FrameRecorder recorder = FrameRecorder.createDefault(outputFile, width, height); //org.bytedeco.javacv.FFmpegFrameRecorder
        System.out.println(recorder.getClass().getName());//org.bytedeco.javacv.FFmpegFrameRecorder
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);// avcodec.AV_CODEC_ID_H264，编码
        recorder.setFormat("flv");//封装格式，如果是推送到rtmp就必须是flv封装格式
        recorder.setFrameRate(25);
        recorder.start();//开启录制器
        long startTime = 0;
        long videoTS;
        CanvasFrame frame = new CanvasFrame("camera", 1); //2.2/2.2=1
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
       // Frame rotatedFrame=converter.convert(MatUtils.bufImg2Mat());
        while (!flag) {
            BufferedImage img=robot.createScreenCapture(rectangle);
            Frame rotatedFrame=converter.convert(bufferedImageToMat(img));
            frame.showImage(img);
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            videoTS = (System.currentTimeMillis() - startTime) * 1000;//这里要注意，注意位
            recorder.setTimestamp(videoTS);
            recorder.record(rotatedFrame);
            Thread.sleep(40);
        }
        recorder.stop();
        recorder.release();
        frame.dispose();
        //grabber.stop();
        //grabber.close();




       /* String str = Loader.load(opencv_objdetect.class);
        System.out.println(str);

        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();
        Frame grabbedImage = grabber.grab();//抓取一帧视频并将其转换为图像，至于用这个图像用来做什么？加水印，人脸识别等等自行添加
        int width = grabbedImage.imageWidth;
        int height = grabbedImage.imageHeight;

        //String outputFile = "d:\\record.mp4";
        String outputFile = "rtmp://47.107.172.13:1935/hls/test";
        FrameRecorder recorder = FrameRecorder.createDefault(outputFile, width, height); //org.bytedeco.javacv.FFmpegFrameRecorder
        System.out.println(recorder.getClass().getName());//org.bytedeco.javacv.FFmpegFrameRecorder
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);// avcodec.AV_CODEC_ID_H264，编码
        recorder.setFormat("flv");//封装格式，如果是推送到rtmp就必须是flv封装格式
        recorder.setFrameRate(25);
        recorder.start();//开启录制器
        long startTime = 0;
        long videoTS;
        CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma()); //2.2/2.2=1
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        Frame rotatedFrame;
        while (frame.isVisible() && (rotatedFrame = grabber.grab()) != null) {
            frame.showImage(rotatedFrame);
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            videoTS = (System.currentTimeMillis() - startTime) * 1000;//这里要注意，注意位
            recorder.setTimestamp(videoTS);
            recorder.record(rotatedFrame);
            Thread.sleep(40);
        }
        recorder.stop();
        recorder.release();
        frame.dispose();
        grabber.stop();
        grabber.close();*/
    }

    public static org.bytedeco.javacpp.opencv_core.Mat bufferedImageToMat(BufferedImage bi) {
        OpenCVFrameConverter.ToMat cv = new OpenCVFrameConverter.ToMat();
        return cv.convertToMat(new Java2DFrameConverter().convert(bi));
    }



}
