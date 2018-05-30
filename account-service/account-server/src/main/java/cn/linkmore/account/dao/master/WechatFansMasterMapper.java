package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.WechatFans;
import cn.linkmore.account.request.ReqWechatFans;

/**
 * 微信粉mapper（写）
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Mapper
public interface WechatFansMasterMapper {
	
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(String id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(WechatFans record);

    /**
     * @Description  新增null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(WechatFans record);

    /**
     * @Description  更新null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(WechatFans record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(WechatFans record);

	/**
	 * @Description  新增通过请求bean
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveReq(ReqWechatFans bean);

	/**
	 * @Description  更新通过请求bean
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateReq(ReqWechatFans bean);
}