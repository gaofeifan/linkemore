package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.AuthRecord;
@Mapper
public interface AuthRecordMasterMapper {

    int save(AuthRecord record);

    int update(AuthRecord record);
    
    int cancelAuth(Long id);
}