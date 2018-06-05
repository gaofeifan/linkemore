package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.RechargeRecord;

/**
 * 储值记录--mapper--写
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Mapper
public interface RechargeRecordMasterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);
}