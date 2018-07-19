package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntRentUser;
/**
 * 长租用户---写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntRentUserMasterMapper {
    int deleteById(Long id);

    int save(EntRentUser record);

    int saveSelective(EntRentUser record);

    EntRentUser findById(Long id);

    int updateByIdSelective(EntRentUser record);

    int updateById(EntRentUser record);
}