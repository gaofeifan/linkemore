package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntAuthStall;
/**
 * 授权车位 -- 读
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntAuthStallClusterMapper {

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    EntAuthStall findById(Long id);

	/**
	 * @Description  根据条件查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<EntAuthStall> findList(Map<String, Object> param);

	/**
	 * 查询员工权限
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	List<EntAuthStall> findByEntStaffId(Long stallId);
}