package cn.linkmore.enterprise.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 车位异常状态 -- 写
 * @author   GFF
 * @Date     2018年8月6日
 * @Version  v2.0
 */
@Mapper
public interface StallExcStatusMasterMapper {

	/**
	 * @Description  更新异常状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateExcStatus(Map<String, Object> map);

}
