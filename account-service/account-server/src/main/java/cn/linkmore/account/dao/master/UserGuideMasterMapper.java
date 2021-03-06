package cn.linkmore.account.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserGuide;
import cn.linkmore.account.request.ReqUserGuide;
@Mapper
public interface UserGuideMasterMapper {
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
    int save(UserGuide record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(UserGuide record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(UserGuide record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(UserGuide record);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveReq(ReqUserGuide userGuide);

	/**
	 * @Description  更新--请求
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateReqById(ReqUserGuide userGuide);

	/**
	 * @Description  批量删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteIds(List<Long> ids);
}