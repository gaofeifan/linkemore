package cn.linkmore.account.dao.cluster;



import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.StaffAppfans;
/**
 * 用户微信(读)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface StaffAppfansClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    StaffAppfans findById(String id);

	/**
	 * @Description  根据用户id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
//	ResUserAppfans findByUserId(Long userId);


}