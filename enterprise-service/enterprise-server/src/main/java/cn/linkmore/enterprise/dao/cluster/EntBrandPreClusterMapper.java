package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPre;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandPreStall;

/**
 * 品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandPreClusterMapper {
	
	/**
	 * 查询普通用户能查看的品牌车区列表
	 * @param paramMap
	 * @return
	 */
	List<ResEntBrandPre> findBrandPre(Map<String, Object> paramMap);
	/**
	 * 查询员工用户能查看的品牌车区列表
	 * @param paramMap
	 * @return
	 */
	List<ResEntBrandPre> findStaffBrandPre(Map<String, Object> paramMap);

    ResBrandPre findById(Long id);

	Integer check(Map<String, Object> param);
	
	List<ResBrandPreStall> findList();
	

}