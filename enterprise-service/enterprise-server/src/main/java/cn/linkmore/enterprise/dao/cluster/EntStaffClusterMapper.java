package cn.linkmore.enterprise.dao.cluster;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntStaff;
/**
 * 企业员工---读mapper
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntStaffClusterMapper {

    EntStaff findById(Long id);

	EntStaff findByMobile(String mobile);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<EntStaff> findPage(Map<String, Object> param);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(Map<String, Object> param);

	/**
	 * @Description  查询所有用户
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<EntStaff> findAll();

}