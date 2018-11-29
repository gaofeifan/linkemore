package cn.linkmore.enterprise.dao.master;

import java.util.List;

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
    int deleteByPrimaryKey(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int insert(RentEnt record);

    /**
     * @Description  新增null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(RentEnt record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByPrimaryKeySelective(RentEnt record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByPrimaryKey(RentEnt record);

	void deleteByIds(List<Long> ids);
}