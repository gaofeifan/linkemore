package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Receive;
import cn.linkmore.account.response.ResReceive;

/**
 * 领券记录(读)
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Mapper
public interface ReceiveClusterMapper {
	/**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    Receive findById(Long id);
    

	/**
	 * @Description	 查询总数  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResReceive> findPage(Map<String, Object> param);


	/**
	 * @Description  详情总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer detailCount(Map<String, Object> param);


	/**
	 * @Description  详情分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResReceive> findDetailPage(Map<String, Object> param);
}