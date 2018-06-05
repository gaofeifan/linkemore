package cn.linkmore.account.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.WalletBanner;
import cn.linkmore.account.request.ReqWalletBanner;
/**
 * banner管理--mapper--写
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Mapper
public interface WalletBannerMasterMapper {
    /**
     * @Description  根据id删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description	新增  
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(WalletBanner record);

    /**
     * @Description  新增null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(WalletBanner record);

    /**
     * @Description  更新null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(WalletBanner record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(WalletBanner record);

	/**
	 * @Description  更新字段
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateColumnValue(@Param("bid")Long bid, @Param("column")String column, @Param("status")int status);

	/**
	 * @Description  新增--请求bean
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveReq(ReqWalletBanner banner);

	/**
	 * @Description  更新状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateStatus(@Param("ids") List<Long> ids,@Param("status") int status);
}