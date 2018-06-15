package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Theme;
import cn.linkmore.coupon.response.ResEnterpriseBean;
import cn.linkmore.coupon.response.ResTheme;
@Mapper
public interface ThemeClusterMapper {

    ResTheme findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResTheme> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);
	/**
	 * 查询企业名称列表
	 * @return
	 */
	List<ResEnterpriseBean> findEnterpriseList();

}