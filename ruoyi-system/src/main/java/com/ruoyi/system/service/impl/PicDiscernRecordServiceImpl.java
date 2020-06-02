package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PicDiscernRecordMapper;
import com.ruoyi.system.domain.PicDiscernRecord;
import com.ruoyi.system.service.IPicDiscernRecordService;
import com.ruoyi.common.core.text.Convert;

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
}
