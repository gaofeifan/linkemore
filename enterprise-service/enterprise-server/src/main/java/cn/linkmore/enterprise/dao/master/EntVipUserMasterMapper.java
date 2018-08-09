package cn.linkmore.enterprise.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntStaff;
import cn.linkmore.enterprise.entity.EntVipUser;

/**
 * 企业vip用户---写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntVipUserMasterMapper {
    int deleteById(Long id);

    int save(EntVipUser record);

    int sveSelective(EntVipUser record);

    EntVipUser findById(Long id);

    int updateByIdSelective(EntVipUser record);

    int updateById(EntVipUser record);
    
    void delete(List<Long> ids);
    
}