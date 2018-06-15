package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
public interface EnterpriseDealService {

	/**
	 * @Description  查询树桩数据
	 * @Author   GFF 
	 * @Date       2018年3月21日
	 * @Param    ContractService
	 * @Return   Tree
	 */
	List<Tree> findTree();

	Map<String, Object> map();

	/**
	 *  保存
	 */
	int save(ReqEnterpriseDeal deal);

	/**
	 *  更新
	 */
	int update(ReqEnterpriseDeal deal);

	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Date       2018年3月22日
	 * @Param    EnterpriseDealService
	 * @Return   void
	 */
	int delete(List<Long> ids);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Date       2018年3月22日
	 * @Param    EnterpriseDealService
	 * @Return   Integer
	 */
	Integer check(ReqCheck reqCheck);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Date       2018年3月22日
	 * @Param    EnterpriseDealService
	 * @Return   ViewPage
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  根据企业id查询
	 * @Author   GFF 
	 * @Date       2018年3月26日
	 * @Param    EnterpriseDealService
	 * @Return   ViewPage
	 */
	List<ResEnterpriseDeal> listByEnterpriseId(Integer enterpriseId,Integer isCreate);


}
