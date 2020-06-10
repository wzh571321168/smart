package com.ruoyi.common.smartutils;


import java.math.BigDecimal;
import java.util.Vector;

import com.ruoyi.common.plate.CharsRecognise;
import com.ruoyi.common.plate.PlateDetect;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_core.Mat;



/**
 * 车牌识别
 * @author eguid
 **/



public class PlateRecognitionUtils {
    static PlateDetect plateDetect =null;
    static CharsRecognise cr=null;
    static{
        plateDetect=new PlateDetect();
        plateDetect.setPDLifemode(true);
        cr = new CharsRecognise();
    }

/**
     * 单个车牌识别
     * @param mat
     * @return
 */



    public static String plateRecognise(Mat mat){
        Vector<Mat> matVector = new Vector<Mat>(1);
        if (0 == plateDetect.plateDetect(mat, matVector)) {
            if(matVector.size()>0){
                return cr.charsRecognise(matVector.get(0));
            }
        }
        return null;
    }
/**
     * 多车牌识别
     * @param mat
     * @return

*/


    public static String[] mutiPlateRecognise(Mat mat){
        PlateDetect plateDetect = new PlateDetect();
        plateDetect.setPDLifemode(true);
        Vector<Mat> matVector = new Vector<Mat>(10);
        if (0 == plateDetect.plateDetect(mat, matVector)) {
            CharsRecognise cr = new CharsRecognise();
            String[] results=new String[matVector.size()];
            for (int i = 0; i < matVector.size(); ++i) {
                String result = cr.charsRecognise(matVector.get(i));
                results[i]=result;
            }
            return results;
        }
        return null;
    }
/**
     * 单个车牌识别
     * @param mat
     * @return
*/



    public static String plateRecognise(String imgPath){
        Mat src = opencv_imgcodecs.imread(imgPath);
        return plateRecognise(src);
    }
/**
     * 多车牌识别
     * @param mat
     * @return
     */


    public static String[] mutiPlateRecognise(String imgPath){
        Mat src = opencv_imgcodecs.imread(imgPath);
        return mutiPlateRecognise(src);
    }

    public static void main(String[] args){
        int sum=100;
        int errNum=0;
        int sumTime=0;
        long longTime=0;
        /*for(int i=sum;i>0;i--){*/
            String imgPath = "D:\\21.jpg";
            Mat src = opencv_imgcodecs.imread(imgPath);
            long now =System.currentTimeMillis();
            String ret=plateRecognise(src);
            System.out.println(ret);
            long s=System.currentTimeMillis()-now;
            if(s>longTime){
                longTime=s;
            }
            sumTime+=s;
            if(!"鲁B995EQ".equals(ret)){
                errNum++;
            }
      /*  }
        System.err.println("总数量："+sum);
        System.err.println("单次最长耗时："+longTime+"ms");

        BigDecimal errSum=new BigDecimal(errNum);
        BigDecimal sumNum=new BigDecimal(sum);
        BigDecimal c=sumNum.subtract(errSum).divide(sumNum).multiply(new BigDecimal(100));
        System.err.println("总耗时："+sumTime+"ms,平均处理时长："+sumTime/sum+"ms,错误数量："+errNum+"，正确识别率："+c+"%");*/
    }
}
