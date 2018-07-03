package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.account.entity.NoticeRead;

/**
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
@Mapper
public interface NoticeReadMasterMapper {
    int deleteById(Long id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(NoticeRead record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(NoticeRead record);


    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(NoticeRead record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(NoticeRead record);

	void updateReadStatus();

}