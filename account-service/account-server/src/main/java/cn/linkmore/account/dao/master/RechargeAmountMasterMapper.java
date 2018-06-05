package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.RechargeAmount;
/**
 * 充值面额--mapper（写）
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Mapper
public interface RechargeAmountMasterMapper {
    /**
     * @Description  根据id删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(RechargeAmount record);

    /**
     * @Description  新增null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(RechargeAmount record);

    /**
     * @Description  更新null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(RechargeAmount record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(RechargeAmount record);
}