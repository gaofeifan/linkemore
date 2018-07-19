package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntAuthStall;
/**
 * 授权车位 -- 写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntAuthStallMasterMapper {
    int deleteById(Long id);

    int save(EntAuthStall record);

    int saveSelective(EntAuthStall record);

    int updateByIdSelective(EntAuthStall record);

    int updateById(EntAuthStall record);
}