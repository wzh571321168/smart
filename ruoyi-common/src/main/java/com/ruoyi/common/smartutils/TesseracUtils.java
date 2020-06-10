package com.ruoyi.common.smartutils;




import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class TesseracUtils {
    /**
     *
     * @param textImage 图片
     * @param ZH_CN 是否使用中文训练库,true-是
     * @return 识别结果
     */
    public static String FindOCR(BufferedImage textImage, boolean ZH_CN) {
        try {
            Tesseract instance=new Tesseract();
            instance.setDatapath("E:\\java\\tesseract-4.0.0-alpha\\tessdata");//设置训练库
            if (ZH_CN) {
                instance.setLanguage("chi_sim");//中文识别
            }
            String result = null;
            result = instance.doOCR(textImage);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "发生未知错误";
        }
    }
}