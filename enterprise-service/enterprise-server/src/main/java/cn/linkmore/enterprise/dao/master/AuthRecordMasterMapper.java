package cn.linkmore.enterprise.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.AuthRecord;
@Mapper
public interface AuthRecordMasterMapper {

    int save(AuthRecord record);

    int update(AuthRecord record);
    
    int cancelAuth(Long id);
    /**
     * 开启1关闭0默认开启 
     * @param param
     * @return
     */
    int operateSwitch(Map<String,Object> param);
}