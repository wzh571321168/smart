package com.ruoyi.system.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.enums.PicDiscernType;
import com.ruoyi.common.smartutils.FaceFeatureUtil;
import com.ruoyi.common.smartutils.MatUtils;
import com.ruoyi.common.smartutils.TesseracUtils;
import com.ruoyi.common.vo.DiscernResultVo;
import org.apache.commons.lang3.StringUtils;
import org.bytedeco.javacpp.opencv_core;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PicDiscernRecordMapper;
import com.ruoyi.system.domain.PicDiscernRecord;
import com.ruoyi.system.service.IPicDiscernRecordService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

/**
 * 图片识别记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-06-02
 */
@Service
public class PicDiscernRecordServiceImpl implements IPicDiscernRecordService 
{
    @Autowired
    private PicDiscernRecordMapper picDiscernRecordMapper;

    /**
     * 查询图片识别记录
     * 
     * @param id 图片识别记录ID
     * @return 图片识别记录
     */
    @Override
    public PicDiscernRecord selectPicDiscernRecordById(Long id)
    {
        return picDiscernRecordMapper.selectPicDiscernRecordById(id);
    }

    /**
     * 查询图片识别记录列表
     * 
     * @param picDiscernRecord 图片识别记录
     * @return 图片识别记录
     */
    @Override
    public List<PicDiscernRecord> selectPicDiscernRecordList(PicDiscernRecord picDiscernRecord)
    {
        return picDiscernRecordMapper.selectPicDiscernRecordList(picDiscernRecord);
    }

    /**
     * 新增图片识别记录
     * 
     * @param picDiscernRecord 图片识别记录
     * @return 结果
     */
    @Override
    public int insertPicDiscernRecord(PicDiscernRecord picDiscernRecord)
    {
        return picDiscernRecordMapper.insertPicDiscernRecord(picDiscernRecord);
    }

    /**
     * 修改图片识别记录
     * 
     * @param picDiscernRecord 图片识别记录
     * @return 结果
     */
    @Override
    public int updatePicDiscernRecord(PicDiscernRecord picDiscernRecord)
    {
        return picDiscernRecordMapper.updatePicDiscernRecord(picDiscernRecord);
    }

    /**
     * 删除图片识别记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePicDiscernRecordByIds(String ids)
    {
        return picDiscernRecordMapper.deletePicDiscernRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除图片识别记录信息
     * 
     * @param id 图片识别记录ID
     * @return 结果
     */
    @Override
    public int deletePicDiscernRecordById(Long id)
    {
        return picDiscernRecordMapper.deletePicDiscernRecordById(id);
    }

    @Autowired
    private FaceFeatureUtil faceFeatureUtil;

    @Value("${pic.path}")
    private String picPath;

    @Override
    public Object picDisCern(Integer type, MultipartFile[] files) throws IOException{
        BufferedImage bufferedImage = ImageIO.read(files[0].getInputStream());
        PicDiscernRecord picDiscernRecord=new PicDiscernRecord();
        picDiscernRecord.setDiscernTime(new Date());
        String picName= UUID.randomUUID().toString();
        String path=picPath+picName+".png";

        MatUtils.writeImageFile(bufferedImage,path);
        picDiscernRecord.setPath(path);
        DiscernResultVo discernResultVo=null;
        if(type== PicDiscernType.FACE.getType()){
            discernResultVo=face(bufferedImage);
        }else if(type== PicDiscernType.CHARACTOR.getType()){
            discernResultVo=charactor(bufferedImage);
        }else if(type== PicDiscernType.COMPARE.getType()){
            BufferedImage bufferedImage2 = ImageIO.read(files[1].getInputStream());
            discernResultVo=compare(bufferedImage,bufferedImage2);
        }
        picDiscernRecord.setResult(JSON.toJSONString(discernResultVo));
        insertPicDiscernRecord(picDiscernRecord);
        return discernResultVo;
    }

    public DiscernResultVo face(BufferedImage bufferedImage){
        opencv_core.Mat mat = MatUtils.toMat(bufferedImage);
        List<opencv_core.Mat> matList = faceFeatureUtil.detectFace(mat);
        DiscernResultVo discernResultVo=new DiscernResultVo();
        String picName= UUID.randomUUID().toString();
        if(matList!=null&&matList.size()>0){
            discernResultVo.setResult("图片中共识别"+matList.size()+"个人脸");
            List<String> list=new ArrayList<>();
            for(int i=1;i<=matList.size();i++){
                String resultPath=picPath+"result/"+picName+"-face"+i+".png";
                imwrite(resultPath, matList.get(i-1));
                list.add(resultPath);
            }
            discernResultVo.setResultPic(list);
        }else {
            discernResultVo.setResult("图片中未识别人脸");
        }
        return discernResultVo;
    }
    public DiscernResultVo charactor(BufferedImage bufferedImage){
        DiscernResultVo discernResultVo=new DiscernResultVo();
        String s = TesseracUtils.FindOCR(bufferedImage, true);
        discernResultVo.setResult(s);
        return discernResultVo;
    }

    public DiscernResultVo compare(BufferedImage bufferedImage1,BufferedImage bufferedImage2){
        DiscernResultVo discernResultVo=new DiscernResultVo();
        List<opencv_core.Mat> matList1 = faceFeatureUtil.detectFace(MatUtils.toMat(bufferedImage1));
        List<opencv_core.Mat> matList2 = faceFeatureUtil.detectFace(MatUtils.toMat(bufferedImage2));
        if(matList1==null||matList1.size()>1||matList2==null||matList2.size()>1){
            discernResultVo.setResult("第一张图片中识别人脸数为"+matList1.size()+"，第二张图片中识别人脸数为"+matList1.size());
            return discernResultVo;
        }else {
            Mat mat1 = new Mat(matList1.get(0).address());
            Mat mat2 = new Mat(matList2.get(0).address());
            double v = faceFeatureUtil.compareImage(mat1, mat2);
            discernResultVo.setResult("相似度："+v);
        }

        return discernResultVo;
    }
}
