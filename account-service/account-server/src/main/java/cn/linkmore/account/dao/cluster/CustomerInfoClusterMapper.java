package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.CustomerInfo;
import cn.linkmore.account.request.ReqCustomerInfoExport;
import cn.linkmore.account.response.ResCustomerInfoExport;

/**
 * 用户数据录入mapper（读）
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Mapper
public interface CustomerInfoClusterMapper {

    /**
     * @Description  通过id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    CustomerInfo findById(Long id);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResCustomerInfoExport> findPage(Map<String, Object> param);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  获取导出数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResCustomerInfoExport> getExportList(ReqCustomerInfoExport pageable);

}