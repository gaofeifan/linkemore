package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.WechatFans;
import cn.linkmore.account.request.ReqWechatFansExcel;
import cn.linkmore.account.response.ResWechatFans;

/**
 * 微信粉mapper（读）
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Mapper
public interface WechatFansClusterMapper {
    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    WechatFans findById(String id);

	/**
	 * @Description  根据条件查询总是
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<WechatFans> findPage(Map<String, Object> param);

	/**
	 * @Description  导出条件
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWechatFans> exportList(ReqWechatFansExcel bean);

}