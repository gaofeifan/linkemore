package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.NoticeContent;

/**
 * 消息内容--写
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
@Mapper
public interface NoticeContentMasterMapper {
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(NoticeContent record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(NoticeContent record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(NoticeContent record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(NoticeContent record);
}