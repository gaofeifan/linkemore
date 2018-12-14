package cn.linkmore.enterprise.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.RentEnt;
/**
 * 长租企业
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
@Mapper
public interface RentEntMasterMapper {
	
    /**
     * @Description  删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int delete(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(RentEnt record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int update(RentEnt record);
    
    /**
     * 批量删除
     * @param ids
     */
	void deleteByIds(List<Long> ids);
	/**
	 * 更新状态
	 * @param map
	 * @return
	 */
	int updateStatus(Map<String, Object> map);
	/**
	 * 更新过期状态
	 */
	void updateOverdueStatus();
}