package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.response.ResBrandApplicant;
/**
 * 品牌申请人
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandApplicantClusterMapper {
	
	/**
	 * 根据企业id和手机号查询是否已申请过品牌认证
	 * @param entId
	 * @param mobile
	 * @return
	 */
	Integer findBrandApplicant(Map<String, Object> map);
    /**
     * 计数
     * @param param
     * @return
     */
	Integer count(Map<String, Object> param);
	/**
	 * 列表
	 * @param param
	 * @return
	 */
	List<ResBrandApplicant> findPage(Map<String, Object> param);
	

}