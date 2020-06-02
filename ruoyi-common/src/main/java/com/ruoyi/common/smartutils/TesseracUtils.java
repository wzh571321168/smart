package com.ruoyi.common.smartutils;


/*

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

public class TesseracUtils {
    public static String ocr(String language,String dataPath, String imageUrl) {

        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();

        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(dataPath, language) != 0) {
            System.err.println("Could not initialize tesseract.");
            return null;
        }

        // Open input image with leptonica library
        PIX image = pixRead(imageUrl);
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        try {
            return outText.getString();
        }finally{
            // Destroy used object and release memory
            api.End();
            outText.deallocate();
            pixDestroy(image);
        }
    }
    public static void main(String[] args) {

//填写语言包eng.traineddata的目录路径以及要识别的图片路径
        String ret= ocr("eng","E:\\java\\tesseract-4.0.0-alpha", "E:\\test\\222.jpg");
        System.out.println("OCR output:\n" +ret);
    }

}
*/
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseracUtils {

    static {
        System.load("E:\\java\\work\\RuoYi\\ruoyi-common\\src\\main\\resources\\cv\\opencv_java412.dll");
    }; // 用来调用OpenCV库文件,必须添加

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        //保存验证码的文件夹
       /* File imgFile = new File("E:\\formPic\\unFormPic");
        //验证码保存地址
        String downAddress = "E:\\formPic\\unFormPic\\";
        //验证码下载地址
        String downURL = "https://www.qichamao.com/usercenter/varifyimage?t=0.6488481170232967";
        if (imgFile.listFiles().length < 400) {
            for (int i = 1; i <= 400; i++) {
                downloadPic(downURL, downAddress + i + ".gif");
                Thread.sleep(10 + (i % 100));
            }
        }

        //获取保存的验证码并转换为tif格式（Tesseract不支持识别gif图片）
        File imgFile0 = new File("E:\\formPic\\unFormPic");
        for (File image : imgFile0.listFiles()) {
            changePicFormat("tif", image, "E:\\formPic\\formedPic\\");
        }
        System.out.println("图片格式转换成功");

        //获取转换为tif格式后的验证码，并进行加工（图片去噪，二值化），增加验证码识别度
        int picNum = 1;
        File imageFile1 = new File("E:\\formPic\\formedPic");
        for (File image : imageFile1.listFiles()) {
            filterPic(image.getName(), picNum + ".tif");
            picNum++;
        }
       */


        //获取加工后的
        File resultImgs = new File("E:\\result_cut");
        for (File link : resultImgs.listFiles()) {
            String reslut = getResult(link);
            System.out.println(link.getName() + "识别结果：" + reslut);
        }

    }

    // 图片处理及处理后的图片储存
    public static void filterPic(String imgName, String fileName) throws FileNotFoundException, IOException {
        // 图片去噪
        Mat src = Imgcodecs.imread("E:\\formPic\\formedPic\\" + imgName, Imgcodecs.IMREAD_UNCHANGED);
        Mat dst = new Mat(src.width(), src.height(), CvType.CV_8UC1);

        if (src.empty()) {
            System.out.println("没有图片");
        } else {
            System.out.println("图片处理成功");
        }

        Imgproc.boxFilter(src, dst, src.depth(), new Size(3.2, 3.2));
        Imgcodecs.imwrite("E:\\filter\\" + fileName, dst);

        // 图片阈值处理，二值化
        Mat src1 = Imgcodecs.imread("E:\\filter\\" + fileName, Imgcodecs.IMREAD_UNCHANGED);
        Mat dst1 = new Mat(src1.width(), src1.height(), CvType.CV_8UC1);

        Imgproc.threshold(src1, dst1, 165, 200, Imgproc.THRESH_TRUNC);
        Imgcodecs.imwrite("E:\\process\\" + fileName, dst1);

        // 图片截取
        Mat src2 = Imgcodecs.imread("E:\\process\\" + fileName, Imgcodecs.IMREAD_UNCHANGED);
        Rect roi = new Rect(4, 2, src2.cols() - 7, src2.rows() - 4); // 参数：x坐标，y坐标，截取的长度，截取的宽度
        Mat dst2 = new Mat(src2, roi);

        Imgcodecs.imwrite("E:\\result_cut\\" + fileName, dst2);

    }

    // 获取验证码
    public static String getResult(File imageFile) {
        if (!imageFile.exists()) {
            System.out.println("图片不存在");
        }
        Tesseract tessreact = new Tesseract();
        tessreact.setDatapath("E:\\java\\tesseract-4.0.0-alpha\\tessdata");
        tessreact.setLanguage("chi_sim");    //将默认库设置为自己训练的库

        String result;
        try {
            result = tessreact.doOCR(imageFile);
            return result;
        } catch (TesseractException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 图片格式转换
     *
     * @param outputFormat
     *            转换的格式
     * @param file
     *            要转换的图片
     * @param downAddress
     *            转换后保存的地址
     * @sourse: http://www.open-open.com/code/view/1453300186683
     */
    public static void changePicFormat(String outputFormat, File image, String downAddress) {

        try {
            BufferedImage bim = ImageIO.read(image);
            File output = new File(
                    downAddress + image.getName().substring(0, image.getName().lastIndexOf(".") + 1) + outputFormat);
            ImageIO.write(bim, outputFormat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载验证码
     *
     * @param picUrl
     *            验证码获取地址
     * @param address
     *            图片保存地址
     */
    public static void downloadPic(String picUrl, String imgAddress) {
        try {
            URL url = new URL(picUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            //需要设置头信息，否则会被识别为机器而获取不到验证码图片
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
            conn.connect();

            int result = -1;
            byte[] buf = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(imgAddress);
            while ((result = bis.read(buf)) != -1) {
                fos.write(buf);
            }
            fos.flush();

            fos.close();
            bis.close();
            System.out.println("图片下载成功");
        } catch (MalformedURLException e) {
            System.out.println("图片读取失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }
    }

}
