package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Account;
/**
 * 账户mapper(读)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface AccountClusterMapper {

    /**
     * 根据id查询
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    Account findById(Long id);

}