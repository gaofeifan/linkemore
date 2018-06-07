package cn.linkmore.order.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.WalletDetail;
import cn.linkmore.order.request.ReqWalletDetailExport;
import cn.linkmore.order.response.ResWalletDetail;
@Mapper
public interface WalletDetailClusterMapper {

    /**
     * 根据id查询
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    WalletDetail findById(Long id);

	/**
	 * @Description 查询总数 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWalletDetail> findPage(Map<String, Object> param);

	/**
	 * @Description  查询excel导出数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWalletDetail> getListByTime(ReqWalletDetailExport bean);
    
}