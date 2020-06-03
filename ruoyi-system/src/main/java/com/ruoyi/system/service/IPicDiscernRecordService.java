package com.ruoyi.system.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import com.ruoyi.system.domain.PicDiscernRecord;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片识别记录Service接口
 * 
 * @author ruoyi
 * @date 2020-06-02
 */
public interface IPicDiscernRecordService 
{
    /**
     * 查询图片识别记录
     * 
     * @param id 图片识别记录ID
     * @return 图片识别记录
     */
    public PicDiscernRecord selectPicDiscernRecordById(Long id);

    /**
     * 查询图片识别记录列表
     * 
     * @param picDiscernRecord 图片识别记录
     * @return 图片识别记录集合
     */
    public List<PicDiscernRecord> selectPicDiscernRecordList(PicDiscernRecord picDiscernRecord);

    /**
     * 新增图片识别记录
     * 
     * @param picDiscernRecord 图片识别记录
     * @return 结果
     */
    public int insertPicDiscernRecord(PicDiscernRecord picDiscernRecord);

    /**
     * 修改图片识别记录
     * 
     * @param picDiscernRecord 图片识别记录
     * @return 结果
     */
    public int updatePicDiscernRecord(PicDiscernRecord picDiscernRecord);

    /**
     * 批量删除图片识别记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePicDiscernRecordByIds(String ids);

    /**
     * 删除图片识别记录信息
     * 
     * @param id 图片识别记录ID
     * @return 结果
     */
    public int deletePicDiscernRecordById(Long id);

    Object picDisCern(Integer type, MultipartFile[] files) throws IOException;
}
