package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.AccessDetail;

/**
 * 接口访问详情
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface AccessDetailMasterMapper {
	
	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int deleteById(Long id);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int insert(AccessDetail record);

	/**
	 * @Description  新增(null处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int insertSelective(AccessDetail record);
	/**
	 * @Description  更新(null处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int updateByIdSelective(AccessDetail record);
	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int updateById(AccessDetail record);
}