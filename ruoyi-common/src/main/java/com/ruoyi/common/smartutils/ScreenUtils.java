package com.ruoyi.common.smartutils;/*
package com.wang.smart.utils;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenUtils {
    public static void main(String[] args) {
        try {
            test();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }

    }
    public static void test() throws FrameGrabber.Exception {
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
        CanvasFrame frame = new CanvasFrame("屏幕录制");// javacv提供的图像展现窗口
        int width = 800;
        int height = 600;
        frame.setBounds((int) (screenSize.getWidth() - width) / 2, (int) (screenSize.getHeight() - height) / 2, width,
                height);// 窗口居中
        frame.setCanvasSize(width, height);// 设置CanvasFrame窗口大小
        while (frame.isShowing()) {
            BufferedImage image = robot.createScreenCapture(rectangle);// 从当前屏幕中读取的像素图像，该图像不包括鼠标光标
            frame.showImage(image);

            try {
                Thread.sleep(45);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        frame.dispose();

    }
}
*/
