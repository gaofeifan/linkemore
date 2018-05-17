package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Account;
@Mapper
public interface AccountMasterMapper {
    int deleteById(Long id);

    int insert(Account record);

    int insertSelective(Account record);


    int updateByIdSelective(Account record);

    int updateById(Account record);
}