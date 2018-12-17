package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.Enterprise;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.security.request.ReqPerson;

public interface EnterpriseService {

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 根据条件查询
	 * 
	 * @param param
	 * @return
	 */
	List<ResEnterprise> findList(Map<String, Object> param);

	/**
	 * 保存
	 * 
	 * @param record
	 */
	int save(ReqEnterprise record);

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	int update(ReqEnterprise record);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(Long id);

	/**
	 * 检验属性存在
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);
	
	/**
	 * @Description  通过所属行业查询区域
	 * @Author   GFF 
	 * @Date       2018年3月21日
	 * @Param    selectRegionByIndustry
	 * @Return   String[]
	 */
	List<ResEnterprise> selectRegionByIndustry(Integer industry);

	/**
	 * @Description  查询所有
	 * @Author   GFF 
	 * @Date       2018年3月22日
	 * @Param    EnterpriseService
	 * @Return   String
	 */
	List<ResEnterprise> selectAll();

	ResEnterprise findById(Long id);

	ResEnterprise findName(Map<String, Object> map);

	/**
	 * @Description  根据id批量查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResEnterprise> findListByIds(List<Long> list);
}
