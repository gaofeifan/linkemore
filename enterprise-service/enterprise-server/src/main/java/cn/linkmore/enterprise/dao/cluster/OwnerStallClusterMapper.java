package cn.linkmore.enterprise.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.EntOwnerPre;
import cn.linkmore.enterprise.entity.EntOwnerStall;

/**
 * 获取车位 -- 读
 * 
 * @author cl
 * @Date 2018年8月13日
 * @Version v1.0
 */
@Mapper
public interface OwnerStallClusterMapper {

	/**
	 * 获取可用的长租车区
	 */
	List<EntOwnerPre> findPre(Long userid);
	
	/**
	 * 获取已登记的长租车位
	 */
	 List<EntOwnerStall> findStall(Long userid);

}