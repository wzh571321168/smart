package com.ruoyi.common.smartutils;


import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatUtils {
    private static OpenCVFrameConverter.ToIplImage  iplConv = new OpenCVFrameConverter.ToIplImage();
    private static OpenCVFrameConverter.ToMat       matConv = new OpenCVFrameConverter.ToMat();
    private static Java2DFrameConverter biConv  = new Java2DFrameConverter();

    /**
     * Clones (deep copies the data) of a {@link BufferedImage}. Necessary when
     * converting to BufferedImages from JavaCV types to avoid re-using the same
     * memory locations.
     *
     * @param source
     * @return
     */
    public static BufferedImage deepCopy(BufferedImage source) {
        return Java2DFrameConverter.cloneBufferedImage(source);
    }

    public synchronized static BufferedImage toBufferedImage(IplImage src) {
        return deepCopy(biConv.getBufferedImage(iplConv.convert(src).clone()));
    }

    public synchronized static BufferedImage toBufferedImage(Mat src) {
        return deepCopy(biConv.getBufferedImage(matConv.convert(src).clone()));
    }



    public synchronized static IplImage toIplImage(Mat src){
        return iplConv.convertToIplImage(matConv.convert(src)).clone();
    }



    public synchronized static IplImage toIplImage(BufferedImage src){
        return iplConv.convertToIplImage(biConv.convert(src)).clone();
    }

    public synchronized static Mat toMat(IplImage src){
        return matConv.convertToMat(iplConv.convert(src).clone());
    }



    public synchronized static Mat toMat(BufferedImage src){
        return matConv.convertToMat(biConv.convert(src)).clone();
    }


    /**
     * 路径图片转为base64
     *
     * @param imagePath
     * @return
     * @throws IOException
     */
    public static Mat imagePath2Mat(String imagePath) throws IOException {
        // 注释部分模拟传入base64数据
        BufferedImage image = ImageIO.read(new FileInputStream(imagePath));
        Mat matImage = toMat(image);// CvType.CV_8UC3
        // 小型图片可以输出 查看下
//		System.out.println(matImage.dump());
//		mat = matImage.dump();
        return matImage;
    }
    /**
     * base64转Mat
     *
     * @param base64
     * @return
     * @throws IOException
     */
    public static Mat base642Mat(String base64) throws IOException {
        // 对base64进行解码
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] origin = decoder.decodeBuffer(base64);
        InputStream in = new ByteArrayInputStream(origin); // 将b作为输入流；
        BufferedImage image = ImageIO.read(in);
        Mat matImage =toMat(image);// CvType.CV_8UC3
        return matImage;
    }


    /**
     *
     * @param base64
     * @throws IOException
     */
    public static Rect base642Rect(String base64) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] origin = decoder.decodeBuffer(base64);
        InputStream in = new ByteArrayInputStream(origin); // 将b作为输入流；
        BufferedImage image = ImageIO.read(in);
        return new Rect(0,0,image.getWidth(),image.getHeight());
    }

    /**
     *
     * @throws IOException
     */
    public static Rect BufferedImage2Rect(BufferedImage image) throws IOException {
        return new Rect(0,0,image.getWidth(),image.getHeight());
    }
    /**
     *
     * @param base64
     * @throws IOException
     */
    public static BufferedImage base642BufferedImage(String base64) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] origin = decoder.decodeBuffer(base64);
        InputStream in = new ByteArrayInputStream(origin); // 将b作为输入流；
        return ImageIO.read(in);
    }

    /**
     * BufferedImage转换成Mat
     *
     * @param original 要转换的BufferedImage
     * @param imgType  bufferedImage的类型 如 BufferedImage.TYPE_3BYTE_BGR
     * @param matType  转换成mat的type 如 CvType.CV_8UC3
     */
    /*public static Mat bufImg2Mat(BufferedImage original, int imgType, int matType) {
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }

        // Don't convert if it already has correct type
        if (original.getType() != imgType) {

            // Create a buffered image
            BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), imgType);

            // Draw the image onto the new buffer
            Graphics2D g = image.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(original, 0, 0, null);
            } finally {
                g.dispose();
            }
        }

        byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
       // Mat mat = Mat.eye(original.getHeight(), original.getWidth(), matType);
        Mat image = new Mat(original.getHeight(), original.getWidth(), matType);
        image.put(0, 0, pixels);
        return image;
    }*/

    /**
     * Mat转换成BufferedImage
     *
     * @param matrix        要转换的Mat
     * @param fileExtension 格式为 ".jpg", ".png", etc
     * @return
     *//*
	public static BufferedImage Mat2BufImg(Mat matrix, String fileExtension) {
		// convert the matrix into a matrix of bytes appropriate for
		// this file extension
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(fileExtension, matrix, mob);
		// convert the "matrix of bytes" into a byte array
		byte[] byteArray = mob.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImage;
	}*/

    /**
     * Mat转换保存成Image
     *
     * @param matrix        要转换的Mat
     * @param fileExtension 格式为 ".jpg", ".png", etc
     *
     * @return
     */
/*	public static void Mat2Img(Mat matrix, String fileExtension, String pathAndName) {
		// convert the matrix into a matrix of bytes appropriate for
		// this file extension
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(fileExtension, matrix, mob);
		// convert the "matrix of bytes" into a byte array
		byte[] byteArray = mob.toArray();

		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			writeImageFile(bufImage, pathAndName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

    /**
     * 将bufferdimage转换为图片
     *
     * @param bi
     * @param pathAndName
     * @throws IOException
     */
    public static void writeImageFile(BufferedImage bi, String pathAndName) throws IOException {
        File outputfile = new File(pathAndName);
        ImageIO.write(bi, "jpg", outputfile);
    }

    private Mat imageUrl2Mat(String imgUrl) {
        URL url;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(imgUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();

            baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inputStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                baos.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return base642Mat(encode(baos.toByteArray()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
    private static String encode(byte[] image){
        BASE64Encoder decoder = new BASE64Encoder();
        return replaceEnter(decoder.encode(image));
    }

    private static String replaceEnter(String str){
        String reg ="[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }



}
