package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.account.entity.WalletDetail;
import cn.linkmore.account.request.ReqWalletDetailExport;
import cn.linkmore.account.request.ResWalletDetail;
/**
 * 充值明细mapper--读
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Mapper
public interface WalletDetailClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    WalletDetail findById(Long id);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWalletDetail> findPage(Map<String, Object> param);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  查询导出数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWalletDetail> getListByTime(ReqWalletDetailExport bean);
}