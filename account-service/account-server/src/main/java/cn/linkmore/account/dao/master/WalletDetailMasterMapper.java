package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.WalletDetail;
/**
 * 充值明细mapper--写
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Mapper
public interface WalletDetailMasterMapper {
    /**
     * @Description  删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(WalletDetail record);

    /**
     * @Description  新增null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(WalletDetail record);

    /**
     * @Description  更新null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(WalletDetail record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(WalletDetail record);
}